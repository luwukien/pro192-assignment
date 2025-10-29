/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vuhuy
 * @param <T>
 */
public abstract class Management<T> {
    protected List<T> list = new ArrayList<>();

    public Management() {}

    public Management(List<T> initialList) {
        this.list = initialList;
    }

    public List<T> getAll() {
        return list;
    }

    public abstract T findById(String id);

    public boolean add(T item) {
        return list.add(item);
    }

    public boolean delete(T item) {
        return list.remove(item);
    }

    public abstract boolean update(T itemToUpdate);
}
