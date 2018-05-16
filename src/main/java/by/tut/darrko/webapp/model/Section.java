package by.tut.darrko.webapp.model;

import by.tut.darrko.webapp.exception.StorageException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {

    abstract void doWriteToDataStream(DataOutputStream dos) throws IOException;

    abstract void doReadFromDataStream(DataInputStream dis) throws IOException;

    public void WriteToDataStream(DataOutputStream dos) {
        try {
            doWriteToDataStream(dos);
        } catch (IOException e) {
            throw new StorageException("Write error", e);
        }
    }

    public void readFromDataStream(DataInputStream dis) {
        try {
            doReadFromDataStream(dis);
        } catch (IOException e) {
            throw new StorageException("Read error", e);
        }
    }
}