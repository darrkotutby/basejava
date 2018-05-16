package by.tut.darrko.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public void doWriteToDataStream(DataOutputStream dos) throws IOException {
        dos.writeInt(organizations.size());
        for (Organization organization : organizations) {
            organization.writeToDataStream(dos);
        }
    }

    @Override
    public void doReadFromDataStream(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Organization organization = new Organization();
            organization.readFromDataStream(dis);
            organizations.add(organization);
        }
    }
}