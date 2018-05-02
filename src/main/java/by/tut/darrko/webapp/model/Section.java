package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public abstract class Section<T extends Entry> {
    private final SectionType sectionType;

    Section(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public abstract void addEntry(T entry);

    public abstract List<Entry> getEntries();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return sectionType == section.sectionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionType);
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionType=" + sectionType +
                '}';
    }

    public void print() throws ParseException {
        System.out.println(sectionType.getTitle());
    }
}
