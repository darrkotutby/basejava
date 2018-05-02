package by.tut.darrko.webapp.model;

import java.text.ParseException;
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
    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "ListedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() throws ParseException {
        super.print();
        StringBuilder sb = new StringBuilder();
        for (Entry entry : entries) {
            sb.append(entry.toStringForPrint()).append("\n");
        }
        String string = sb.toString().replace(", \n", "\n");
        System.out.println(string.substring(0, string.length() - 1));
        System.out.println();
    }

}
