import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private final int MAX_SIZE = 10000;
    private int size = 0;

    private Resume[] storage = new Resume[MAX_SIZE];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= MAX_SIZE) {
            System.out.println("Storage is full");
            return;
        }

        int resumeIndex = findResumeElementNumber(resume.uuid);
        if (resumeIndex != -1) {
            System.out.println("Resume already exists");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        System.out.println("Resume doesn't exists");
        return null;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex != -1) {
            storage[resumeIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume doesn't exists");
        }


    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(String oldUUID, String newUUID) {
        int resumeIndex = findResumeElementNumber(oldUUID);
        if (resumeIndex != -1) {
            storage[resumeIndex].uuid = newUUID;
        } else System.out.println("Resume doesn't exists");
    }

    private int findResumeElementNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }

        }
        return -1;
    }

}