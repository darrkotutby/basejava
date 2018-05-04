package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OrganisationSection implements Section<List<OrganisationDatedEntry>> {

    private List<OrganisationDatedEntry> entries = new ArrayList<>();

    @Override
    public void addEntry(List<OrganisationDatedEntry> entry) {
        entries.addAll(entry);
    }

    @Override
    public List<OrganisationDatedEntry> getContent() {
        return entries;
    }

    @Override
    public String toString() {
        return "OrganisationSection{" +
                "entries=" + entries +
                "} " + super.toString();
    }

    public void print() {
        Set<OrganisationDatedEntry> set = new TreeSet<>(entries);
        for (OrganisationDatedEntry organisationDatedEntry : set) {
            System.out.println(organisationDatedEntry.getOrganisationName());
            System.out.println(organisationDatedEntry.toStringForPrint());
        }
        System.out.println();
    }
}
