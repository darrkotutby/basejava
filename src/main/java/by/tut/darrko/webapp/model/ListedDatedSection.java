package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ListedDatedSection extends Section<DatedEntry> {

    // private List<Entry> entries = new ArrayList<>();
    private Map<String, List<DatedEntry>> entries = new TreeMap<>();

    ListedDatedSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void addEntry(DatedEntry entry) {
        List<DatedEntry> list = entries.computeIfAbsent(entry.getOrganisation(), k -> new ArrayList<>());
        list.add(entry);
    }

    @Override
    public List<Entry> getEntries(DatedEntry entry) {
        List<Entry> list = new ArrayList<>();
        for (String organisation : entries.keySet()) {
            list.addAll(entries.get(organisation));
        }
        return list;
    }

    @Override
    public String toString() {
        return "ListedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        super.print();
        for (String organisation : entries.keySet()) {
            System.out.println(organisation + ":");
            for (DatedEntry datedEntry : entries.get(organisation)) {
                System.out.println(datedEntry.getDateFrom() + "\t" + datedEntry.getDateTo() + "\t" + datedEntry.getPosition());
                System.out.println("\t" + datedEntry.getDescription());
            }
            System.out.println();
        }
        System.out.println();
    }
}
