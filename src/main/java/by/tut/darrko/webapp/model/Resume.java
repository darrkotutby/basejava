package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.*;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<SectionType, Section> sections = new TreeMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "FullName has to be not null");
        Objects.requireNonNull(uuid, "Uuid has to be not null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    private List getContacts() {
        return sections.get(SectionType.CONTACTS).getEntries();
    }

    public void addContact(ContactType contactType, String description) {
        getSection(SectionType.CONTACTS).addEntry(new ContactEntry(contactType, description));
    }

    public List<ContactEntry> getContactsByType(ContactType contactType) {
        List<ContactEntry> list = getContacts();
        if (contactType == null) {
            return list;
        }
        List<ContactEntry> subList = new ArrayList<>();
        for (ContactEntry contact : list) {
            if (contact.getContactType().equals(contactType))
                subList.add(contact);
        }
        return subList;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    private Section getSection(SectionType sectionType) {
        Section section = sections.get(sectionType);
        if (section == null) {
            section = SectionFactory.createSection(sectionType);
            sections.put(sectionType, section);
        }
        return section;
    }

    public void addSectionsEntry(SectionType sectionType, String description) {
        getSection(sectionType).addEntry(new Entry(description));
    }

    public void addSectionsEntry(SectionType sectionType, String organisationName, Date dateFrom, String dateTo, String position, String
            description) {
        getSection(sectionType).addEntry(new DatedEntry(organisationName, dateFrom, dateTo, position, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "\nuuid='" + uuid + '\'' +
                ",\n fullName='" + fullName + '\'' +
                //  ",\n contacts=" + contacts +
                ",\n sections=" + sections +
                "\n}";
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public void print() throws ParseException {
        System.out.println(fullName);
        System.out.println();
        for (SectionType contactType : sections.keySet()) {
            sections.get(contactType).print();
        }
    }
}
