package by.tut.darrko.webapp.model;

import java.text.ParseException;
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
    public List<Entry> getEntries() {
        List<Entry> list = new ArrayList<>();
        list.add(entry);
        return list;
    }

    public void print() throws ParseException {
        super.print();
        String string = null;
        try {
            string = entry.toStringForPrint().replace(", \n", "\n");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(string.substring(0,string.length()-2));
        System.out.println();
    }
}
