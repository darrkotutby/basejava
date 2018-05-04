package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private EnumMap<SectionType, Section> sections = new EnumMap<>(SectionType.class);

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

    private Section getDefaultSection(SectionType sectionType) {
        Section section = null;
        switch (sectionType) {
            case PERSONAL:
            case ACHIEVEMENT:
            case QUALIFICATION:
                section = new ListedSection();
                break;
            case EXPERIENCE:
            case EDUCATION:
                section = new DatedSection();
                break;
            default:
                section = new SimpleSection();

        }
        sections.put(sectionType, section);
        return section;
    }

    public Section getSection(SectionType sectionType) {
        return sections.getOrDefault(sectionType, getDefaultSection(sectionType));
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

        System.out.println(fullName);
        System.out.println();

        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle() + entry.getValue());
        }

        System.out.println();

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            entry.getValue().print();
        }
    }
}
