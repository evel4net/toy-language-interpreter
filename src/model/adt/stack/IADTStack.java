package model.adt.stack;

import java.util.List;

public interface IADTStack<T> {
    T pop() throws EmptyStackException;
    void push(T value);
    T top() throws EmptyStackException;

    boolean isEmpty();
    int getSize();

    List<T> getAll();

    String toString();
}
