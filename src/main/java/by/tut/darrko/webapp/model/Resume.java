package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class Resume implements Comparable<Resume> {

    private static EnumMap<SectionType, String> sectionTypeMap = new EnumMap<>(SectionType.class);
    private static EnumMap<ContactType, String> contactTypeMap = new EnumMap<>(ContactType.class);

    static {
        sectionTypeMap.put(SectionType.OBJECTIVE, "Позиция:");
        sectionTypeMap.put(SectionType.PERSONAL, "Личные качества:");
        sectionTypeMap.put(SectionType.ACHIEVEMENT, "Достжения:");
        sectionTypeMap.put(SectionType.QUALIFICATION, "Квалификация:");
        sectionTypeMap.put(SectionType.EXPERIENCE, "Опыт работы:");
        sectionTypeMap.put(SectionType.EDUCATION, "Образование:");

        contactTypeMap.put(ContactType.ADDRESS, "Адрес: ");
        contactTypeMap.put(ContactType.PHONE, "Телефон: ");
        contactTypeMap.put(ContactType.EMAIL, "E-mail: ");
        contactTypeMap.put(ContactType.SKYPE, "SKYPE :");
    }

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private Map<ContactType, String> contacts = new TreeMap<>();
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


    public void addContact(ContactType contactType, String description) {
        contacts.put(contactType, description);
    }

    public String getContactsByType(ContactType contactType) {
        return contacts.get(contactType);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    private void addSection(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE:
                sections.put(sectionType, new SimpleSection());
                break;
            case PERSONAL:
            case ACHIEVEMENT:
            case QUALIFICATION:
                sections.put(sectionType, new ListedSection());
                break;
            case EXPERIENCE:
            case EDUCATION:
                sections.put(sectionType, new DatedSection());
        }
    }

    private Section getSection(SectionType sectionType) {
        Section section = sections.get(sectionType);
        if (section == null) {
            addSection(sectionType);
            section = sections.get(sectionType);
        }
        return section;
    }

    public void addSectionsEntry(SectionType sectionType, String description) {
        getSection(sectionType).addEntry(description);
    }

    public void addSectionsEntry(SectionType sectionType, String organisationName, LocalDate dateFrom, String dateTo, String position, String
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
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public void print() throws ParseException {

        System.out.println(contactTypeMap);
        System.out.println(sectionTypeMap);

        System.out.println();


        System.out.println(fullName);
        System.out.println();

        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(contactTypeMap.get(entry.getKey()) + entry.getValue());
        }

        System.out.println();

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(sectionTypeMap.get(entry.getKey()));
            entry.getValue().print();
        }
    }
}
