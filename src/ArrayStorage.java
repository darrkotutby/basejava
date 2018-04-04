/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private int maxSize = 10000;
    private int current = -1;

    private Resume[] storage = new Resume[maxSize];

    void clear() {
        current = -1;
        storage = new Resume[maxSize];
    }

    void save(Resume r) {
        if (current<maxSize) {
            current++;
            storage[current] = r;
        }
    }

    Resume get(String uuid) {
       for (int i=0; i<=current; i++) {
           if (storage[i].uuid.equals(uuid)) {
               return storage[i];
           }
       }
       return null;
    }

    void delete(String uuid) {
        for (int i=0; i<=current; i++) {
            if (storage[i].uuid.equals(uuid)) {
                if (i!=current) {
                    storage[i]=storage[current];
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
        Resume[] storage1 = new Resume[current+1];
        for (int i=0; i<=current; i++) {
            storage1[i] = storage[i];
        }
        return storage1;
    }

    int size() {
        return current+1;
    }

}
