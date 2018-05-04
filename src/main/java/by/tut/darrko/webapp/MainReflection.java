package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.DatedEntry;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.model.SectionType;
import by.tut.darrko.webapp.util.DateUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        r.getSection(SectionType.OBJECTIVE).addEntry("Ведущий инженер программист");

        List<String> list = new ArrayList<>();
        list.add("Дотошный");
        list.add("Упорный");
        r.getSection(SectionType.PERSONAL).addEntry(list);

        list = new ArrayList<>();
        list.add("Разработка информационной системы");
        r.getSection(SectionType.ACHIEVEMENT).addEntry(list);

        list = new ArrayList<>();
        list.add("Oracle, SQL, PL/SQL");
        list.add("Java, C++");
        r.getSection(SectionType.QUALIFICATION).addEntry(list);

        List<DatedEntry> list1 = new ArrayList<>();
        list1.add(new DatedEntry("Банк", DateUtil.stringToDate("02.01.2000"), DateUtil.stringToDate("31.12.2003"), "Ведущий программист", "Oracle forms"));
        list1.add(new DatedEntry("Банк", DateUtil.stringToDate("10.01.2004"), null, "Главный программист", "Oracle forms"));
        r.getSection(SectionType.EXPERIENCE).addEntry(list1);

        list1 = new ArrayList<>();
        list1.add(new DatedEntry("РТИ", DateUtil.stringToDate("04.01.2000"), DateUtil.stringToDate("01.08.2005"), "Студент", "ФКП"));
        list1.add(new DatedEntry("РТИ", DateUtil.stringToDate("03.01.1995"), DateUtil.stringToDate("01.08.2000"), "Студент", "ФКП"));
        list1.add(new DatedEntry("javaops", DateUtil.stringToDate("04.01.2017"), null, "Студент", "JAVA, JSP, SQL"));
        r.getSection(SectionType.EDUCATION).addEntry(list1);

        System.out.println(r);
        System.out.println();
        r.print();

    }
}
