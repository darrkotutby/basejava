package by.tut.darrko.webapp.model;

import java.text.ParseException;

public interface Section<T> {

    void addEntry(T t);

    T getContent();

    void print() throws ParseException;
}
