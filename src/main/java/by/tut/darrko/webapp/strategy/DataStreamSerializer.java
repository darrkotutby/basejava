package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationMethod {

    private static <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            T item = (T) reader.getItem();
            list.add(item);
        }
        return list;
    }

    private static <T> void writeList(List<T> list, DataOutputStream dos, Writer<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T t1 : list) {
            writer.writeItem(t1);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeSection(entry.getKey(), entry.getValue(), dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(sectionType, dis));
            }
            return resume;
        }
    }

    private void writeSection(SectionType sectionType, Section section, DataOutputStream dos) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeList(((ListSection) section).getItems(), dos, dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeList(((OrganizationSection) section).getOrganizations(), dos, organization -> {
                    dos.writeUTF(organization.getHomePage().getName());
                    dos.writeUTF(organization.getHomePage().getUrl() == null ? "" : organization.getHomePage().getUrl());
                    writeList(organization.getPositions(), dos, position -> {
                        dos.writeUTF(position.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        dos.writeUTF(position.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
                    });
                });
                break;
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);
        }
    }

    private Section readSection(SectionType sectionType, DataInputStream dis) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE: {
                TextSection textSection = new TextSection();
                textSection.setContent(dis.readUTF());
                return textSection;
            }
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                ListSection listSection = new ListSection();
                listSection.setItems(readList(dis, dis::readUTF));
                return listSection;
            }
            case EXPERIENCE:
            case EDUCATION: {
                OrganizationSection organizationSection = new OrganizationSection();
                organizationSection.setOrganizations(readList(dis, () -> {
                    String name = dis.readUTF();
                    String homePage = dis.readUTF();
                    if (homePage.equalsIgnoreCase("")) {
                        homePage = null;
                    }
                    Organization organization = new Organization(name, homePage);
                    organization.setPositions(readList(dis, () -> {
                        LocalDate startDate = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        LocalDate endDate = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        if (description.equalsIgnoreCase("")) {
                            description = null;
                        }
                        return new Organization.Position(startDate, endDate, title, description);
                    }));
                    return organization;
                }));
                return organizationSection;
            }
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);
        }
    }

    @FunctionalInterface
    public interface Reader<T> {
        T getItem() throws IOException;
    }

    @FunctionalInterface
    public interface Writer<T> {
        void writeItem(T t) throws IOException;
    }

}
