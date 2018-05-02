package by.tut.darrko.webapp.model;

import java.util.Objects;

public class Contact {
    private final ContactType contactType;
    private final String description;

    Contact(ContactType contactType, String description) {
        this.contactType = contactType;
        this.description = description;
    }

    ContactType getContactType() {
        return contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return contactType == contact.contactType &&
                Objects.equals(description, contact.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(contactType, description);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactType=" + contactType +
                ", description='" + description + '\'' +
                '}';
    }

    void print() {
        System.out.println(contactType.getTitle() + " " + description);
    }

}
