package model.adt.list;

import java.util.List;
import java.util.Vector;

public class ADTSynchronizedList<T> implements IADTList<T> {
    private final List<T> list = new Vector<>();

    @Override
    public void insert(int index, T value) throws InvalidIndexException {
        try {
            this.list.add(index, value);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(index);
        }
    }

    @Override
    public void insertFirst(T value) {
        this.list.addFirst(value);
    }

    @Override
    public void insertLast(T value) {
        this.list.add(value);
    }

    @Override
    public T remove(T value) throws InvalidIndexException {
        int index = this.list.indexOf(value);

        try {
            return this.list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(index);
        }
    }

    @Override
    public T removeAt(int index) throws InvalidIndexException {
        try {
            return this.list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(index);
        }
    }

    @Override
    public T removeFirst() throws InvalidIndexException {
        return this.removeAt(0);
    }

    @Override
    public T removeLast() throws InvalidIndexException {
        return this.removeAt(this.list.size() - 1);
    }

    @Override
    public T get(int index) throws InvalidIndexException {
        try {
            return this.list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(index);
        }
    }

    @Override
    public T replace(int index, T newValue) throws InvalidIndexException {
        try {
            return this.list.set(index, newValue);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(index);
        }
    }

    @Override
    public List<T> getAll() {
        return List.copyOf(this.list);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int getSize() {
        return this.list.size();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
