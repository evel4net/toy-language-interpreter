package model.adt.stack;

public interface IADTStack<T> {
    T pop() throws EmptyStackException;
    void push(T value);
    T top() throws EmptyStackException;

    boolean isEmpty();
    int getSize();

    String toString();
}
