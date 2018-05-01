package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            ClassNotFoundException, InstantiationException {
        String resumeClassName = "by.tut.darrko.webapp.model.Resume";
        Class<?> resumeClass = Class.forName(resumeClassName);
        Constructor<?> dogConstructor = resumeClass.getConstructor(String.class);
        Object resume = dogConstructor.newInstance("test1");
        Method getUuidMethod = resume.getClass().getMethod("getUuid");
        System.out.println("getUuid: " + getUuidMethod.invoke(resume));
        getUuidMethod = resume.getClass().getMethod("toString");
        System.out.println("toString: " + getUuidMethod.invoke(resume));

        Resume r = new Resume("UUID1", "Alex Ivanov");
        r.addContact(ContactType.ADDRESS, "Minsk");
        r.addContact(ContactType.PHONE, "123");
        r.addContact(ContactType.PHONE, "456");
        r.addObjective("Ведущий инженер программист");
        r.addPersonal("Тупой");
        r.addPersonal("Упоротый");

        System.out.println(r);
    }
}
