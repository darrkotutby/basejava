package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.Objects;

public class DatedEntry implements Comparable<DatedEntry> {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String position;
    private String description;

    public DatedEntry(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
        this.description = description;
    }

    LocalDate getDateFrom() {
        return (dateFrom == null) ? LocalDate.now() : dateFrom;
    }

    LocalDate getDateTo() {
        return (dateTo == null) ? LocalDate.now() : dateTo;
    }

    String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatedEntry that = (DatedEntry) o;
        return Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateFrom, dateTo, position);
    }

    @Override
    public String toString() {
        return "DatedEntry{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(DatedEntry datedEntry) {
        int cmp = datedEntry.getDateFrom().compareTo(getDateFrom());
        if (cmp != 0) {
            return cmp;
        }
        cmp = datedEntry.getDateTo().compareTo(getDateTo());
        if (cmp != 0) {
            return cmp;
        }
        return position.compareTo(datedEntry.position);
    }

    public String toStringForPrint() {
        return DateUtil.dateToString(dateFrom, "MM.yyy") + " - " +
                (dateTo == null ?
                        "NOW" : DateUtil.dateToString(dateTo, "MM.yyyy")) +
                "\t" + position + "\n\t" + description;
    }
}
