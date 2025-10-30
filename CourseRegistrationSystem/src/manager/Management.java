package manager;

import interfaces.Identifiable;
import java.util.List;

/**
 * @param <T>
 */
public abstract class Management<T extends Identifiable> {

    protected List<T> list;

    public Management(List<T> initialList) {
        this.list = initialList;
    }

    public List<T> getAll() {
        return this.list;
    }

    public T findById(String id) {
        for (T item : list) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean add(T item) {
        String newId = item.getId();
        if (findById(newId) != null) {
            System.out.println("Found this id: " + item.getId() + " exist!");
            return false;
        }
        this.list.add(item);
        return true;
    }

    public boolean delete(String id) {
        T itemFound = findById(id);
        if (findById(id) == null) {
            System.out.println("This this id: " + id + " is not exist!");
            return false;
        }

        this.list.remove(itemFound);
        return true;
    }

    public boolean update(T itemToUpdate) {
        String id = itemToUpdate.getId();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getId().equals(id)) {
                this.list.set(i, itemToUpdate);
                return true;
            }
        }
        System.out.println("Not found this id: " + id + "to update");
        return false;
    }

}