import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    final private int MAX_SIZE = 10000;
    private int lastResume = -1;

    private Resume[] storage = new Resume[MAX_SIZE];

    void clear() {
        Arrays.fill(storage, 0, lastResume, null);
        lastResume = -1;
    }

    void save(Resume r) {
        if (lastResume < MAX_SIZE) {
            lastResume++;
            storage[lastResume] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= lastResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i <= lastResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                if (i != lastResume) {
                    storage[i] = storage[lastResume];
                }
                storage[lastResume] = null;
                lastResume--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        return lastResume + 1;
    }

}
