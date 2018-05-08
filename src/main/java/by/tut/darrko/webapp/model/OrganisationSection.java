package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OrganisationSection implements Section<List<OrganisationEntry>> {

    private List<OrganisationEntry> entries = new ArrayList<>();

    @Override
    public void addEntry(List<OrganisationEntry> entry) {
        entries.addAll(entry);
    }

    @Override
    public List<OrganisationEntry> getContent() {
        return entries;
    }

    @Override
    public String toString() {
        return "OrganisationSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        Set<OrganisationEntry> set = new TreeSet<>(entries);
        for (OrganisationEntry organisationEntry : set) {
            System.out.println(organisationEntry.getOrganisationName());
            System.out.println(organisationEntry.toStringForPrint());
        }
        System.out.println();
    }
}
