package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.Objects;

public class Entry implements Comparable<Entry> {
    private String description;

    Entry(String description) {
        this.description = description;
    }

    String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry that = (Entry) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Entry entry) {
        return description.compareTo(entry.description);
    }

    public String toStringForPrint() throws ParseException {
        return description + ", ";
    }


}
