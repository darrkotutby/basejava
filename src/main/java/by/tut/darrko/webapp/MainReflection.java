package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.util.FileUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Month;

public class MainReflection {

    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            ClassNotFoundException, InstantiationException {

        File directory = new File("").getAbsoluteFile();

        System.out.println(directory.getAbsolutePath());
        FileUtil.recursiveDirPrint(directory, "");

        String resumeClassName = "by.tut.darrko.webapp.model.Resume";
        Class<?> resumeClass = Class.forName(resumeClassName);
        Constructor<?> dogConstructor = resumeClass.getConstructor(String.class);
        Object resume = dogConstructor.newInstance("test1");
        Method getUuidMethod = resume.getClass().getMethod("getUuid");
        System.out.println("getUuid: " + getUuidMethod.invoke(resume));
        getUuidMethod = resume.getClass().getMethod("toString");
        System.out.println("toString: " + getUuidMethod.invoke(resume));

        Resume r = new Resume("UUID1", "Alex Ivanov");
        r.addContact(ContactType.PHONE, "456");
        r.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий инженер программист"));
        r.addSection(SectionType.PERSONAL, new TextSection("Дотошный, упорный"));

        r.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        r.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        r.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));


        r.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1",
                                        "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY,
                                        "position2", "content2"))));


        r.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));


        System.out.println(r);
    }
}