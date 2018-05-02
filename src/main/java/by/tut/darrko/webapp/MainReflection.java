package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainReflection {

    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            ClassNotFoundException, InstantiationException, ParseException {
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
        r.addPersonal("Упорный");
        r.addPersonal("Дотошный");
        r.addAchievement("Разработка информационной системы");
        r.addQualification("Oracle, SQL, PL/SQL");
        r.addQualification("Java, C++");
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH);
        r.addExperience("Банк", format.parse("01.01.2000"),"NOW", "Ведущий программист", "Oracle forms");
        r.addEducation("РТИ", format.parse("01.01.1995"),"01.08.2000", "Студент", "ФКП");

        System.out.println(r);
        System.out.println();
        r.print();

    }
}
