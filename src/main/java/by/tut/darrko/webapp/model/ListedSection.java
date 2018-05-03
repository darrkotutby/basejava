package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListedSection implements Section<List<String>> {

    private List<String> entries = new ArrayList<>();

    @Override
    public void addEntry(List<String> entry) {
        entries.addAll(entry);
    }

    @Override
    public List<String> getContent() {
        return entries;
    }

    @Override
    public String toString() {
        return "ListedSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        System.out.println(entries.toString().replace("[", "").replace("]", ""));
        System.out.println();
    }
}
