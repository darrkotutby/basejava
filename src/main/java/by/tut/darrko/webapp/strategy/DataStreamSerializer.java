package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.exception.StorageException;
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
                Section section = readSection(sectionType, dis);
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

    public void writeSection(SectionType sectionType, Section section, DataOutputStream dos) {
        switch (sectionType) {
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeList(((ListSection) section).getItems(), dos, new StringReaderWriter());
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeList(((OrganizationSection) section).getOrganizations(), dos, new OrganizationReaderWriter());
                break;
            case PERSONAL:
            case OBJECTIVE:
                try {
                    dos.writeUTF(((TextSection) section).getContent());
                } catch (IOException e) {
                    throw new StorageException("Write error", e);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);

        }
    }

    public Section readSection(SectionType sectionType, DataInputStream dis) {
        switch (sectionType) {
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                ListSection listSection = new ListSection();
                listSection.setItems(readList(dis, new StringReaderWriter()));
                return listSection;
            }
            case EXPERIENCE:
            case EDUCATION: {
                OrganizationSection organizationSection = new OrganizationSection();
                organizationSection.setOrganizations(readList(dis, new OrganizationReaderWriter()));
                return organizationSection;
            }
            case PERSONAL:
            case OBJECTIVE: {
                try {
                    TextSection testSection = new TextSection();
                    testSection.setContent(dis.readUTF());
                    return testSection;
                } catch (IOException e) {
                    throw new StorageException("Read error", e);
                }
            }
            default:
                throw new IllegalArgumentException("Unknown section type:" + sectionType);

        }

    }

    public <T> List<T> readList(DataInputStream dis, ReaderWriter readerWriter) {
        try {
            int size = dis.readInt();
            List<T> items = new ArrayList<>();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    items.add((T) readerWriter.getItem(dis));
                }
            }
            return items;
        } catch (IOException e) {
            throw new StorageException("Write error", e);
        }
    }

    public <T> void writeList(List<T> list, DataOutputStream dos, ReaderWriter readerWriter) {
        try {
            dos.writeInt(list.size());
            for (T t : list) {
                readerWriter.writeItem(t, dos);
            }
        } catch (IOException e) {
            throw new StorageException("Write error", e);
        }
    }

    public class StringReaderWriter implements ReaderWriter<String> {

        @Override
        public String getItem(DataInputStream dis) {
            try {
                return dis.readUTF();
            } catch (IOException e) {
                throw new StorageException("Read error", e);
            }
        }

        @Override
        public void writeItem(String s, DataOutputStream dos) {
            try {
                dos.writeUTF(s);
            } catch (IOException e) {
                throw new StorageException("Write error", e);
            }
        }
    }

    public class OrganizationReaderWriter implements ReaderWriter<Organization> {

        @Override
        public Organization getItem(DataInputStream dis) {
            try {
                String name = dis.readUTF();
                String homePage = dis.readUTF();
                if (homePage.equalsIgnoreCase("NULL")) {
                    homePage = null;
                }
                Organization organization = new Organization(name, homePage);
                organization.setPositions(readList(dis, new OrganizationPositionReaderWriter()));
                return organization;
            } catch (IOException e) {
                throw new StorageException("Read error", e);
            }
        }

        @Override
        public void writeItem(Organization organization, DataOutputStream dos) {
            try {
                dos.writeUTF(organization.getHomePage().getName());
                dos.writeUTF(organization.getHomePage().getUrl() == null ? "NULL" : organization.getHomePage().getUrl());
                writeList(organization.getPositions(), dos, new OrganizationPositionReaderWriter());
            } catch (IOException e) {
                throw new StorageException("Write error", e);
            }
        }
    }

    public class OrganizationPositionReaderWriter implements ReaderWriter<Organization.Position> {

        @Override
        public Organization.Position getItem(DataInputStream dis) {
            try {
                LocalDate startDate = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                LocalDate endDate = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String title = dis.readUTF();
                String description = dis.readUTF();
                if (description.equalsIgnoreCase("NULL")) {
                    description = null;
                }
                return new Organization.Position(startDate, endDate, title, description);
            } catch (IOException e) {
                throw new StorageException("Read error", e);
            }
        }

        @Override
        public void writeItem(Organization.Position position, DataOutputStream dos) {
            try {
                dos.writeUTF(position.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                dos.writeUTF(position.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription() == null ? "NULL" : position.getDescription());
            } catch (IOException e) {
                throw new StorageException("Write error", e);
            }

        }
    }
}
