package by.tut.darrko.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {
    public abstract void writeUTF(DataOutputStream dos);
    public abstract void readUTF(DataInputStream dis);
}