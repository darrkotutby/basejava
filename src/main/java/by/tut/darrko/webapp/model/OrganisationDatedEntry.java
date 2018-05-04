package by.tut.darrko.webapp.model;

import java.time.LocalDate;
import java.util.*;

public class OrganisationDatedEntry implements Comparable<OrganisationDatedEntry> {

    List<DatedEntry> entries = new ArrayList<>();
    private String organisationName;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public OrganisationDatedEntry(String organisationName) {
        this.organisationName = organisationName;
    }

    String getOrganisationName() {
        return organisationName;
    }

    LocalDate getDateFrom() {
        return (dateFrom == null) ? LocalDate.now() : dateFrom;
    }

    LocalDate getDateTo() {
        return (dateTo == null) ? LocalDate.now() : dateTo;
    }

    public void addEntries(List<DatedEntry> entries) {
        for (DatedEntry entry : entries) {
            this.entries.add(entry);
            if (dateFrom == null || dateFrom.compareTo(entry.getDateFrom()) > 0) {
                dateFrom = entry.getDateFrom();
            }

            if (dateTo == null || dateTo.compareTo(entry.getDateTo()) < 0) {
                dateTo = entry.getDateTo();
            }
        }
    }

    public List<DatedEntry> getEntries() {
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationDatedEntry that = (OrganisationDatedEntry) o;
        return Objects.equals(organisationName, that.organisationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationName);
    }

    @Override
    public int compareTo(OrganisationDatedEntry organisationDatedEntry) {
        int cmp = organisationDatedEntry.getDateFrom().compareTo(getDateFrom());
        if (cmp != 0) {
            return cmp;
        }
        cmp = organisationDatedEntry.getDateTo().compareTo(getDateTo());
        if (cmp != 0) {
            return cmp;
        }
        return organisationName.compareTo(organisationDatedEntry.getOrganisationName());
    }

    @Override
    public String toString() {
        return "OrganisationDatedEntry{" +
                "organisationName='" + organisationName + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
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
