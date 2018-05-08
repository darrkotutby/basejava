package by.tut.darrko.webapp.model;

import java.time.LocalDate;
import java.util.*;

public class OrganisationEntry implements Comparable<OrganisationEntry> {

    List<DatedEntry> entries = new ArrayList<>();
    private String organisationName;

    public OrganisationEntry(String organisationName) {
        this.organisationName = organisationName;
    }

    String getOrganisationName() {
        return organisationName;
    }

    LocalDate getDateFrom() {
        Set<DatedEntry> set = new TreeSet<>(entries);
        return ((TreeSet<DatedEntry>) set).last().getDateTo();
    }

    LocalDate getDateTo() {
        Set<DatedEntry> set = new TreeSet<>(entries);
        return ((TreeSet<DatedEntry>) set).first().getDateFrom();
    }

    public void addEntries(List<DatedEntry> entries) {
        this.entries.addAll(entries);
    }

    public List<DatedEntry> getEntries() {
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationEntry that = (OrganisationEntry) o;
        return Objects.equals(organisationName, that.organisationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationName);
    }

    @Override
    public int compareTo(OrganisationEntry organisationEntry) {
        int cmp = organisationEntry.getDateFrom().compareTo(getDateFrom());
        if (cmp != 0) {
            return cmp;
        }
        cmp = organisationEntry.getDateTo().compareTo(getDateTo());
        if (cmp != 0) {
            return cmp;
        }
        return organisationName.compareTo(organisationEntry.getOrganisationName());
    }

    @Override
    public String toString() {
        return "OrganisationEntry{" +
                "organisationName='" + organisationName + '\'' +
                ", entries=" + entries +
                '}';
    }

    public String toStringForPrint() {
        Set<DatedEntry> set = new TreeSet<>(entries);
        StringBuilder sb = new StringBuilder();
        for (DatedEntry entry : set) {
            sb.append(entry.toStringForPrint()).append("\n");
        }
        return sb.toString();
    }
}
