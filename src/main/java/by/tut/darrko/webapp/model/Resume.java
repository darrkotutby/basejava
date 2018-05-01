package by.tut.darrko.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private Map<ContactType, Section<ContactType>> contacts = new TreeMap<>();
    private Map<SectionType, Section<SectionType>> sections = new TreeMap<>();

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

    public Map<ContactType, Section<ContactType>> getContacts() {
        return contacts;
    }

    public void addContact(ContactType contactType, String description) {
        getContactSection(contactType).addEntry(description);
    }

    public void deleteContact(ContactType contactType, String description) {
        getContactSection(contactType).deleteEntry(description);
    }

    public Map<SectionType, Section<SectionType>> getSections() {
        return sections;
    }

    public Section<ContactType> getContactSection(ContactType contactType) {
        Section<ContactType> section = contacts.get(contactType);
        if (section == null) {
            section = new Section<>(contactType);
            contacts.put(contactType, section);
        }
        return section;
    }

    public Section<SectionType> getSection(SectionType sectionType) {
        Section<SectionType> section = sections.get(sectionType);
        if (section == null) {
            section = new Section<>(sectionType);
            sections.put(sectionType, section);
        }
        return section;
    }

    public void addObjective(String description) {
        getSection(SectionType.OBJECTIVE).addEntry(description);
    }

    public void deleteObjective(String description) {
        getSection(SectionType.OBJECTIVE).deleteEntry(description);
    }

    public void addPersonal(String description) {
        getSection(SectionType.PERSONAL).addEntry(description);
    }

    public void deletePersonal(String description) {
        getSection(SectionType.PERSONAL).deleteEntry(description);
    }

    public void addAchievement(String description) {
        getSection(SectionType.ACHIEVEMENT).addEntry(description);
    }

    public void deleteAchievement(String description) {
        getSection(SectionType.ACHIEVEMENT).deleteEntry(description);
    }

    public void addQualification(String description) {
        getSection(SectionType.QUALIFICATION).addEntry(description);
    }

    public void deleteQualification(String description) {
        getSection(SectionType.QUALIFICATION).deleteEntry(description);
    }

    public void addExperience(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EXPERIENCE).addEntry(organisationName, dateFrom, dateTo, position, description);
    }

    public void deleteExperience(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EXPERIENCE).deleteEntry(organisationName, dateFrom, dateTo, position, description);
    }

    public void addEducation(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EDUCATION).addEntry(organisationName, dateFrom, dateTo, position, description);
    }

    public void deleteEducation(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        getSection(SectionType.EDUCATION).deleteEntry(organisationName, dateFrom, dateTo, position, description);
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
}
