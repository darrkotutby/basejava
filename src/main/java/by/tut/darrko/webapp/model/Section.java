package by.tut.darrko.webapp.model;

import java.text.ParseException;
import java.util.List;

public interface Section<T> {

    void addEntry(T t);
    List<T> getEntries();
    void print() throws ParseException;
}
