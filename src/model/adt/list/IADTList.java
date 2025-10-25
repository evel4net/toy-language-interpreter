package model.adt.list;

import java.util.List;

public interface IADTList<T> {
    void insert(int index, T value) throws InvalidIndexException;
    void insertFirst(T value);
    void insertLast(T value);

    T remove(T value) throws InvalidIndexException;
    T removeAt(int index) throws InvalidIndexException;
    T removeFirst() throws InvalidIndexException;
    T removeLast() throws InvalidIndexException;

    T get(int index) throws InvalidIndexException;
    T replace(int index, T newValue) throws InvalidIndexException;

    List<T> getAll();

    boolean isEmpty();
    int getSize();

    String toString();
}
