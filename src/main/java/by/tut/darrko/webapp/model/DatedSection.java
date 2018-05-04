package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatedSection implements Section<List<DatedEntry>> {

    private List<DatedEntry> entries = new ArrayList<>();

    @Override
    public void addEntry(List<DatedEntry> entry) {
        entries.addAll(entry);
    }

    @Override
    public List<DatedEntry> getContent() {
        return entries;
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
