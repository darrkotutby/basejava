package by.tut.darrko.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private List<Contact> contacts = new ArrayList<>();
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(ContactType contactType, String description) {
        contacts.add(new Contact(contactType, description));
    }

    public void deleteContact(ContactType contactType, String description) {
        contacts.remove(new Contact(contactType, description));
    }

    public List<Contact> getContactsByType(ContactType contactType) {
        if (contactType == null) {
            return getContacts();
        }
        List<Contact> list = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getContactType().equals(contactType))
                list.add(contact);
        }
        return list;
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

    public void addObjective(String description) {
        getSection(SectionType.OBJECTIVE).addEntry(new Entry(description));
    }

    public void addPersonal(String description) {
        getSection(SectionType.PERSONAL).addEntry(new Entry(description));
    }

    public void addAchievement(String description) {
        getSection(SectionType.ACHIEVEMENT).addEntry(new Entry(description));
    }

    public void addQualification(String description) {
        getSection(SectionType.QUALIFICATION).addEntry(new Entry(description));
    }

    public void addExperience(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EXPERIENCE).addEntry(new DatedEntry(organisationName, dateFrom, dateTo, position, description));
    }

    public void addEducation(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EDUCATION).addEntry(new DatedEntry(organisationName, dateFrom, dateTo, position, description));
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
                ",\n contacts=" + contacts +
                ",\n sections=" + sections +
                "\n}";
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public void print() {
        System.out.println(fullName);
        System.out.println();

        for (Contact contact : contacts) {
            contact.print();
        }

        System.out.println();
        for (SectionType contactType : sections.keySet()) {
            sections.get(contactType).print();
        }
    }
}
