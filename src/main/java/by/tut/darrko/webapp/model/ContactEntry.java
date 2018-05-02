package by.tut.darrko.webapp.model;

import java.util.Objects;

public class ContactEntry extends Entry{
    private final ContactType contactType;

    ContactEntry(ContactType contactType, String description) {
        super(description);
        this.contactType = contactType;
    }

    ContactType getContactType() {
        return contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ContactEntry contact = (ContactEntry) o;
        return contactType == contact.contactType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contactType);
    }

    @Override
    public String toString() {
        return "ContactEntry{" +
                "contactType=" + contactType +
                "} " + super.toString();
    }

    @Override
    public String toStringForPrint() {
        return contactType.getTitle() + getDescription();
    }
}
