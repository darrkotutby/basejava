package by.tut.darrko.webapp.strategy;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface ReaderWriter<T> {
    T getItem(DataInputStream dis);

    void writeItem(T t, DataOutputStream dos);
}
