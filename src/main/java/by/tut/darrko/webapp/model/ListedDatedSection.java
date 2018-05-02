package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.*;

public class ListedDatedSection extends Section<DatedEntry> {

    private Map<String, Set<DatedEntry>> entries = new TreeMap<>();

    ListedDatedSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void addEntry(DatedEntry entry) {
        Set<DatedEntry> set = entries.computeIfAbsent(entry.getOrganisation(), k -> new TreeSet<>());
        set.add(entry);
    }

    @Override
    public List<Entry> getEntries() {
        List<Entry> list = new ArrayList<>();
        for (String organisation : entries.keySet()) {
            list.addAll(entries.get(organisation));
        }
        return list;
    }

    @Override
    public String toString() {
        return "ListedDatedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() throws ParseException {
        super.print();
        for (String organisation : entries.keySet()) {
            System.out.println(organisation + ":");
            for (DatedEntry datedEntry : entries.get(organisation)) {
                System.out.println(datedEntry.toStringForPrint());
            }
            System.out.println();
        }
    }
}
