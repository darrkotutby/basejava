package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListedSection implements Section<String> {

    private List<String> entries = new ArrayList<>();

    @Override
    public void addEntry(String entry) {
        entries.add(entry);
    }

    @Override
    public List<String> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "ListedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        System.out.println(entries);
    }

}
