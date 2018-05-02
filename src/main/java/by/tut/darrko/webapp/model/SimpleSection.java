package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleSection extends Section<Entry> {

    private Entry entry;

    SimpleSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public String toString() {
        return "SimpleSection{" +
                "entry='" + entry + '\'' +
                "} " + super.toString();
    }

    public void addEntry(Entry entry) {
        this.entry = entry;
    }

    @Override
    public List<Entry> getEntries(Entry entry) {
        List<Entry> list = new ArrayList<>();
        list.add(entry);
        return list;
    }

    public void print() {
        super.print();
        System.out.println(entry.getDescription());
        System.out.println();
    }
}
