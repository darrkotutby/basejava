package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListedSection extends Section<Entry> {

    private List<Entry> entries = new ArrayList<>();

    ListedSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    @Override
    public List<Entry> getEntries(Entry entry) {
        return entries;
    }

    @Override
    public String toString() {
        return "ListedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        super.print();
        StringBuilder sb = new StringBuilder();
        for (Entry entry : entries) {
            sb.append(entry.getDescription()).append(", ");
        }
        String string = sb.toString().trim();
        System.out.println(string.substring(0, string.length() - 1));
        System.out.println();
    }

}
