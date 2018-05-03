package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DatedSection implements Section<DatedEntry> {

    private Set<DatedEntry> entries = new TreeSet<>();

    @Override
    public void addEntry(DatedEntry entry) {
        entries.add(entry);
    }

    @Override
    public List<DatedEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public String toString() {
        return "DatedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() throws ParseException {
        for (DatedEntry datedEntry : entries) {
            System.out.println(datedEntry.getOrganisation());
            System.out.println(datedEntry.toStringForPrint());
        }
        System.out.println();
    }
}
