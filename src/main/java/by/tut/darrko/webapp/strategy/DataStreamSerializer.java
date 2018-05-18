package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationMethod {

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
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
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
                    dos.writeUTF(organization.getHomePage().getUrl());
                    writeList(organization.getPositions(), dos, position -> {
                        dos.writeUTF(position.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        dos.writeUTF(position.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription());
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
                return new ListSection().setItems(readList(dis, dis::readUTF));
            }
            case EXPERIENCE:
            case EDUCATION: {
                return new OrganizationSection()
                        .setOrganizations(readList(dis, () ->
                                new Organization(dis.readUTF(), dis.readUTF())
                                        .setPositions(readList(dis, () ->
                                                new Organization.Position(
                                                        LocalDate.parse(dis.readUTF(),
                                                                DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                                        LocalDate.parse(dis.readUTF(),
                                                                DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                                        dis.readUTF(),
                                                        dis.readUTF())))));
            }
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);
        }
    }

    private <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            T item = reader.getItem();
            list.add(item);
        }
        return list;
    }

    private <T> void writeList(List<T> list, DataOutputStream dos, Writer<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T t1 : list) {
            writer.writeItem(t1);
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
