package by.tut.darrko.webapp.model;

import java.util.*;

public class Section<T> {
    T type;
    Set<Entry> entries = new TreeSet<>();

    public Section(T type) {
        this.type = type;
    }

    public void addEntry(String description) {
        entries.add(new Entry(description));
    }

    public void addEntry(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        entries.add(new DatedEntry(organisationName, dateFrom, dateTo, position, description));
    }

    public void deleteEntry(String description) {
        entries.remove(new Entry(description));
    }

    public void deleteEntry(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        entries.remove(new DatedEntry(organisationName, dateFrom, dateTo, position, description));
    }

    public List<Entry> getAllEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return type == section.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Section{" +
                "type=" + type +
                ", entries=" + entries +
                '}';
    }
}
