package by.tut.darrko.webapp.strategy;

import java.io.DataOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface Writer<T> {
    void writeItem(T t, DataOutputStream dos) throws IOException;
}
