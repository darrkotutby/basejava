import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest {

    private ArrayStorage arrayStorage;
    private Resume r;

    @BeforeEach
    void init() {
        arrayStorage = new ArrayStorage();
        r = new Resume();
        r.uuid = "Test1";
    }

    @Test
    @DisplayName("Save")
    void Save() {
        assertEquals(0, arrayStorage.size());

        arrayStorage.save(r);
        assertEquals(1, arrayStorage.size());
        Resume test = arrayStorage.get(r.uuid);
        assertNotNull(test);
        assertEquals(r.uuid, test.uuid);
    }

    @Test
    @DisplayName("Get")
    void Get() {
        arrayStorage.save(r);
        Resume test = arrayStorage.get("Test3");
        assertNull(test);
        test = arrayStorage.get(r.uuid);
        assertNotNull(test);
        assertEquals(r.uuid, test.uuid);
    }

    @Test
    @DisplayName("Delete")
    void Delete() {
        arrayStorage.save(r);
        Resume r1 = new Resume();
        r1.uuid = "Test2";
        arrayStorage.save(r1);
        arrayStorage.delete("Test3");
        assertEquals(2, arrayStorage.size());
        arrayStorage.delete(r.uuid);
        assertEquals(1, arrayStorage.size());
        arrayStorage.delete(r1.uuid);
        assertEquals(0, arrayStorage.size());

    }

    @Test
    @DisplayName("GetAll")
    void GetAll() {
        arrayStorage.save(r);
        Resume r1 = new Resume();
        r1.uuid = "Test2";
        arrayStorage.save(r1);
        Resume[] rs = arrayStorage.getAll();
        assertEquals(arrayStorage.size(), rs.length);
    }

    @Test
    @DisplayName("Clear")
    void Clear() {
        arrayStorage.save(r);
        Resume r1 = new Resume();
        r1.uuid = "Test2";
        arrayStorage.save(r1);
        assertEquals(2, arrayStorage.size());
        arrayStorage.clear();
        assertEquals(0, arrayStorage.size());
    }

}