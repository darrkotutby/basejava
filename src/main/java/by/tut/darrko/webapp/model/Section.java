package by.tut.darrko.webapp.model;

public interface Section<T> {

    void addEntry(T t);

    T getContent();

    void print();
}
