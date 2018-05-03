package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.util.DateUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Objects;

public class DatedEntry implements Comparable<DatedEntry> {

    private String organisationName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String position;
    private String description;

    public DatedEntry(String organisationName, LocalDate dateFrom, LocalDate dateTo, String position, String description) {
        this.organisationName = organisationName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
        this.description = description;
    }

    String getOrganisation() {
        return organisationName;
    }

    LocalDate getDateFrom() {
        return dateFrom;
    }

    LocalDate getDateTo() {
        return dateTo;
    }

    String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatedEntry)) return false;
        DatedEntry that = (DatedEntry) o;
        return Objects.equals(organisationName, that.organisationName) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {

        return Objects.hash(organisationName, dateFrom, dateTo, position);
    }

    @Override
    public String toString() {
        return "DatedEntry{" +
                "organisationName='" + organisationName + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(DatedEntry datedEntry) {
        int cmp = dateFrom.compareTo(datedEntry.getDateFrom());
        if (cmp != 0) {
            return cmp;
        }
        LocalDate thisDateTo = dateTo == null ? LocalDate.now() : dateTo;
        LocalDate otherDateTo = datedEntry.dateTo == null ? LocalDate.now() : datedEntry.dateTo;;
        cmp = thisDateTo.compareTo(otherDateTo);
        if (cmp != 0) {
            return cmp;
        }
        cmp = organisationName.compareTo(datedEntry.getOrganisation());
        if (cmp != 0) {
            return cmp;
        }
        return position.compareTo(datedEntry.position);
    }

    public String toStringForPrint() throws ParseException {
        return DateUtil.dateToString(dateFrom, "MM.yyy") + " - " +
                (dateTo==null ?
                        "NOW" : DateUtil.dateToString(dateTo, "MM.yyyy")) +
                "\t" + position + "\n\t" + description;
    }
}
