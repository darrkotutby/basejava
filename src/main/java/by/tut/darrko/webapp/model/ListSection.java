package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.exception.StorageException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 14.07.2016
 */
public class ListSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<String> items;

    public ListSection() {
        items = new ArrayList<>();
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public void writeUTF(DataOutputStream dos) {
        try {
            dos.writeInt(items.size());
            for (String item : items) {
                dos.writeUTF(item);
            }
        } catch (IOException e) {
            throw new StorageException("Write error", e);
        }

    }

    @Override
    public void readUTF(DataInputStream dis) {
        try {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
        } catch (IOException e) {
            throw new StorageException("Read error", e);
        }

    }
}

