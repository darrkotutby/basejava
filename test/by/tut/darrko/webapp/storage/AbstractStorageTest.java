package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractStorageTest {

    Resume resume1 = new Resume("UUID1", "Alex Ivanov");
    Resume resume2 = new Resume("UUID2", "Petr Sidorov");
    Resume resume3 = new Resume("UUID3", "Herman Shults");
    Storage storage;
    //  Resume[] array = null;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {

        resume1.addContact(ContactType.ADDRESS, "Minsk");
        resume1.addContact(ContactType.PHONE, "456");
        resume1.getSection(SectionType.OBJECTIVE).addEntry("Ведущий инженер программист");
        resume1.getSection(SectionType.PERSONAL).addEntry("Дотошный, упорный");

        List<String> list = new ArrayList<>();
        list.add("Разработка информационной системы");
        resume1.getSection(SectionType.ACHIEVEMENT).addEntry(list);

        list = new ArrayList<>();
        list.add("Oracle, SQL, PL/SQL");
        list.add("Java, C++");
        resume1.getSection(SectionType.QUALIFICATION).addEntry(list);


        List<DatedEntry> datedEntryList = new ArrayList<>();
        datedEntryList.add(new DatedEntry(DateUtil.stringToDate("02.01.2000"), DateUtil.stringToDate("31.12.2003"), "Ведущий программист", "Oracle forms"));
        datedEntryList.add(new DatedEntry(DateUtil.stringToDate("10.01.2004"), null, "Главный программист", "Oracle forms"));

        OrganisationEntry bank = new OrganisationEntry("Банк");
        bank.addEntries(datedEntryList);
        List<OrganisationEntry> organisationEntryList = new ArrayList<>();
        organisationEntryList.add(bank);

        resume1.getSection(SectionType.EXPERIENCE).addEntry(organisationEntryList);

        List<DatedEntry> datedEntryList1 = new ArrayList<>();
        datedEntryList1.add(new DatedEntry(DateUtil.stringToDate("04.01.2000"), DateUtil.stringToDate("01.08.2005"), "Студент", "ФКП"));
        datedEntryList1.add(new DatedEntry(DateUtil.stringToDate("03.01.1995"), DateUtil.stringToDate("01.08.2000"), "Студент", "ФКП"));
        OrganisationEntry rti = new OrganisationEntry("РТИ");
        rti.addEntries(datedEntryList1);

        List<OrganisationEntry> organisationEntryList1 = new ArrayList<>();
        organisationEntryList1.add(rti);

        List<DatedEntry> datedEntryList2 = new ArrayList<>();
        datedEntryList2.add(new DatedEntry(DateUtil.stringToDate("04.01.2017"), null, "Студент", "JAVA, JSP, SQL"));
        OrganisationEntry javaops = new OrganisationEntry("javaops");
        javaops.addEntries(datedEntryList2);
        organisationEntryList1.add(javaops);

        resume1.getSection(SectionType.EDUCATION).addEntry(organisationEntryList1);


        storage.clear();
        storage.save(resume1);
        storage.save(resume3);
        storage.save(resume2);
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getTest() {
        assertEquals(resume1, storage.get(resume1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistsTest() {
        storage.get("test1");
    }

    @Test
    public void save() {
        Resume newResume = new Resume("test1", "Dummy");
        storage.save(newResume);
        assertEquals(4, storage.size());
        assertEquals(newResume, storage.get(newResume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(resume1.getUuid());
        } catch (NotExistStorageException e) {
            fail(e.getMessage());
        }
        assertEquals(2, storage.size());
        storage.get(resume1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete("test1");
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(resume1.getUuid(), "Test");
        storage.update(newResume);
        assertEquals(newResume.getFullName(), storage.get(resume1.getUuid()).getFullName());

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume("test1"));
    }

    @Test
    public void getAllSortedTest() {
        assertEquals(Arrays.asList(resume1, resume3, resume2), storage.getAllSorted());
    }
}