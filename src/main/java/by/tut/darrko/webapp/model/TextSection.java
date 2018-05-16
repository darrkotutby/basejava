package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.exception.StorageException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TextSection extends Section {

    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return content.equals(that.content);

    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public void writeUTF(DataOutputStream dos) {
        try {
            dos.writeUTF(content);
        } catch (IOException e) {
            throw new StorageException("Write error", e);
        }
    }

    @Override
    public void readUTF(DataInputStream dis) {
        try {
            content = dis.readUTF();
        } catch (IOException e) {
            throw new StorageException("Read error", e);
        }
    }
}

