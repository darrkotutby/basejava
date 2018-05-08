package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.util.DateUtil;
import by.tut.darrko.webapp.util.FileUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        r.addContact(ContactType.ADDRESS, "Minsk");
        r.addContact(ContactType.PHONE, "456");
        r.getSection(SectionType.OBJECTIVE).addEntry("Ведущий инженер программист");
        r.getSection(SectionType.PERSONAL).addEntry("Дотошный, упорный");

        List<String> list = new ArrayList<>();
        list.add("Разработка информационной системы");
        r.getSection(SectionType.ACHIEVEMENT).addEntry(list);

        list = new ArrayList<>();
        list.add("Oracle, SQL, PL/SQL");
        list.add("Java, C++");
        r.getSection(SectionType.QUALIFICATION).addEntry(list);


        List<DatedEntry> datedEntryList = new ArrayList<>();
        datedEntryList.add(new DatedEntry(DateUtil.stringToDate("02.01.2000"), DateUtil.stringToDate("31.12.2003"), "Ведущий программист", "Oracle forms"));
        datedEntryList.add(new DatedEntry(DateUtil.stringToDate("10.01.2004"), null, "Главный программист", "Oracle forms"));

        OrganisationDatedEntry bank = new OrganisationDatedEntry("Банк");
        bank.addEntries(datedEntryList);
        List<OrganisationDatedEntry> organisationDatedEntryList = new ArrayList<>();
        organisationDatedEntryList.add(bank);

        r.getSection(SectionType.EXPERIENCE).addEntry(organisationDatedEntryList);

        List<DatedEntry> datedEntryList1 = new ArrayList<>();
        datedEntryList1.add(new DatedEntry(DateUtil.stringToDate("04.01.2000"), DateUtil.stringToDate("01.08.2005"), "Студент", "ФКП"));
        datedEntryList1.add(new DatedEntry(DateUtil.stringToDate("03.01.1995"), DateUtil.stringToDate("01.08.2000"), "Студент", "ФКП"));
        OrganisationDatedEntry rti = new OrganisationDatedEntry("РТИ");
        rti.addEntries(datedEntryList1);

        List<OrganisationDatedEntry> organisationDatedEntryList1 = new ArrayList<>();
        organisationDatedEntryList1.add(rti);

        List<DatedEntry> datedEntryList2 = new ArrayList<>();
        datedEntryList2.add(new DatedEntry(DateUtil.stringToDate("04.01.2017"), null, "Студент", "JAVA, JSP, SQL"));
        OrganisationDatedEntry javaops = new OrganisationDatedEntry("javaops");
        javaops.addEntries(datedEntryList2);
        organisationDatedEntryList1.add(javaops);

        r.getSection(SectionType.EDUCATION).addEntry(organisationDatedEntryList1);

        System.out.println(r);
        System.out.println();
        r.print();
    }
}
