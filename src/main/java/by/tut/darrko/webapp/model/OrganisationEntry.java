package by.tut.darrko.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganisationEntry {

    List<DatedEntry> entries = new ArrayList<>();
    private String organisationName;

    public OrganisationEntry(String organisationName) {
        this.organisationName = organisationName;
    }

    String getOrganisationName() {
        return organisationName;
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
    public String toString() {
        return "OrganisationEntry{" +
                "organisationName='" + organisationName + '\'' +
                ", entries=" + entries +
                '}';
    }

    public String toStringForPrint() {
        StringBuilder sb = new StringBuilder();
        for (DatedEntry entry : entries) {
            sb.append(entry.toStringForPrint()).append("\n");
        }
        return sb.toString();
    }
}
