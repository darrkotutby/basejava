import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    final private int Max_Size = 10000;
    private int current = -1;

    private Resume[] storage = new Resume[Max_Size];

    void clear() {
        Arrays.fill(storage, 0, current, null);
        current = -1;
    }

    void save(Resume r) {
        if (current < Max_Size) {
            current++;
            storage[current] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= current; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i <= current; i++) {
            if (storage[i].uuid.equals(uuid)) {
                if (i != current) {
                    storage[i] = storage[current];
                }
                storage[current] = null;
                current--;
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
        return current + 1;
    }

}
