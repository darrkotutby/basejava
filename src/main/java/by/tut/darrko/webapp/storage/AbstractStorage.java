package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Comparator<Resume> FULL_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK findResumeElementNumber(String uuid);

    public abstract Resume getByIndex(SK index);

    protected abstract void saveByIndex(Resume resume, SK index);

    protected abstract void deleteByIndex(SK index);

    protected abstract void updateByIndex(Resume resume, SK index);

    abstract boolean check(SK index);

    private SK isExists(String uuid) {
        LOG.info("isExists uuid=" + uuid);
        SK index = findResumeElementNumber(uuid);
        if (!check(index)) {
            LOG.warning("Resume with uuid=" + uuid + " doesn't exists");
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists");
        }
        return index;
    }

    private SK isNotExists(String uuid) {
        LOG.info("isNotExists uuid=" + uuid);
        SK index = findResumeElementNumber(uuid);
        if (check(index)) {
            LOG.warning("Resume with uuid=" + uuid + " already exists");
            throw new ExistStorageException("Resume with uuid=" + uuid + " already exists");
        }
        return index;
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get uuid=" + uuid);
        return getByIndex(isExists(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        Resume[] array = getAll();
        Arrays.sort(array, FULL_NAME_COMPARATOR);
        return Arrays.asList(array);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("save resume=" + resume);
        saveByIndex(resume, isNotExists(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete uuid=" + uuid);
        deleteByIndex(isExists(uuid));
    }

    @Override
    public void update(Resume resume) {
        LOG.info("update resume=" + resume);
        updateByIndex(resume, isExists(resume.getUuid()));
    }
}
