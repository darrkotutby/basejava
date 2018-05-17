package by.tut.darrko.webapp.strategy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
@FunctionalInterface
public interface Reader<T> {
    T getItem(DataInputStream dis) throws IOException;
}
