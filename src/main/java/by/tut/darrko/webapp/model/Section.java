package by.tut.darrko.webapp.model;

import java.util.List;

public interface Section<T> {
    public abstract void addEntry(T t);

    public abstract List<T> getEntries();
}
