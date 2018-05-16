package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.model.Section;
import by.tut.darrko.webapp.model.SectionType;

import java.io.*;
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
                entry.getValue().WriteToDataStream(dos);
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
                Section section = resume.getDefaultSection(SectionType.valueOf(dis.readUTF()));
                section.readFromDataStream(dis);
            }
            return resume;
        }
    }
}
