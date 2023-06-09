/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Resume with uuid " + r.uuid + "already exists");
                return;
            }
        }

        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[size];
        System.arraycopy(storage, 0, allResumes, 0, allResumes.length);
        return allResumes;
    }

    int size() {
        return size;
    }
}