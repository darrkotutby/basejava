package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleSection implements Section<String> {

    private String entry;

    @Override
    public String toString() {
        return "SimpleSection{" +
                "entry='" + entry + '\'' +
                "} " + super.toString();
    }

    public void addEntry(String entry) {
        this.entry = entry;
    }

    @Override
    public String getContent() {
        return entry;
    }

    public void print() {
        System.out.println(entry);
        System.out.println();
    }
}
