import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private final int MAX_SIZE = 10000;
    private int size = 0;

    private Resume[] storage = new Resume[MAX_SIZE];

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= MAX_SIZE) {
            System.out.println("Storage is full");
            return;
        }

        int i = findResumeElementNumber(r.uuid);
        if (i != -1) {
            System.out.println("Resume already exists");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int i = findResumeElementNumber(uuid);
        if (i != -1) return storage[i];
        System.out.println("Resume doesn't exists");
        return null;
    }

    public void delete(String uuid) {
        int i = findResumeElementNumber(uuid);
        if (i != -1) {
            if (i < size - 1) {
                storage[i] = storage[size - 1];
            }
            storage[size - 1] = null;
            size--;
        } else System.out.println("Resume doesn't exists");


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
        int i = findResumeElementNumber(oldUUID);
        if (i != -1) {
            storage[i].uuid = newUUID;
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