package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.model.SectionType;

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
        r.addSectionsEntry(SectionType.OBJECTIVE, "Ведущий инженер программист");
        r.addSectionsEntry(SectionType.PERSONAL, "Упорный");
        r.addSectionsEntry(SectionType.PERSONAL, "Дотошный");
        r.addSectionsEntry(SectionType.ACHIEVEMENT, "Разработка информационной системы");
        r.addSectionsEntry(SectionType.QUALIFICATION, "Oracle, SQL, PL/SQL");
        r.addSectionsEntry(SectionType.QUALIFICATION, "Java, C++");
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH);
        r.addSectionsEntry(SectionType.EXPERIENCE, "Банк", format.parse("02.01.2000"), "31.12.2003", "Ведущий программист", "Oracle forms");
        r.addSectionsEntry(SectionType.EXPERIENCE, "Банк", format.parse("10.01.2004"), "NOW", "Главный программист", "Oracle forms");
        r.addSectionsEntry(SectionType.EDUCATION, "РТИ", format.parse("03.01.1995"), "01.08.2000", "Студент", "ФКП");
        r.addSectionsEntry(SectionType.EDUCATION, "РТИ", format.parse("04.01.2000"), "01.08.2005", "Студент", "ФКП");
        r.addSectionsEntry(SectionType.EDUCATION, "javaops", format.parse("04.01.2017"), "NOW", "Студент", "JAVA, JSP, SQL");

        System.out.println(r);
        System.out.println();
        r.print();

        System.out.println(System.getProperty("user.dir"));

    }
}
