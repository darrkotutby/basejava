package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.util.DateUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class DatedEntry extends Entry {
    private String organisationName;
    private Date dateFrom;
    private String dateTo;
    private String position;

    DatedEntry(String organisationName, Date dateFrom, String dateTo, String position, String description) {
        super(description);
        this.organisationName = organisationName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.position = position;
    }

    String getOrganisation() {
        return organisationName;
    }

    Date getDateFrom() {
        return dateFrom;
    }

    String getDateTo() {
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
                ", description='" + getDescription() + '\'' +
                '}';
    }

    @Override
    public int compareTo(Entry entry) {
        DatedEntry datedEntry = (DatedEntry) entry;

        int cmp = dateFrom.compareTo(datedEntry.getDateFrom());
        if (cmp != 0) {
            return cmp;
        }
        LocalDate thisDateTo = null;
        if (dateTo == null || dateTo.equalsIgnoreCase("NOW")) {
            thisDateTo = LocalDate.now();
        } else {
            try {
                thisDateTo = DateUtil.stringToDate(dateTo).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } catch (ParseException e) {
                try {
                    thisDateTo = DateUtil.stringToDate("01.01.1900").toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }
        LocalDate otherDateTo = null;
        if (datedEntry.dateTo == null || datedEntry.dateTo.equalsIgnoreCase("NOW")) {
            otherDateTo = LocalDate.now();
        } else {
            try {
                otherDateTo = DateUtil.stringToDate(datedEntry.dateTo).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } catch (ParseException e) {
                try {
                    otherDateTo = DateUtil.stringToDate("01.01.1900").toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }
        assert thisDateTo != null;
        assert otherDateTo != null;
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
        return DateUtil.dateToString(dateFrom, "mm.yyy") + " - " +
                (dateTo.equalsIgnoreCase("NOW") ?
                        "NOW" : DateUtil.dateToString(DateUtil.stringToDate(dateTo), "mm.yyyy")) +
                "\t" + position + "\n\t" + getDescription();
    }
}
