package model.adt.stack;

import java.util.LinkedList;
import java.util.List;

public class ADTStack<T> implements IADTStack<T> {
    private final List<T> stack = new LinkedList<>();

    @Override
    public T pop() throws EmptyStackException {
        try {
            return this.stack.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new EmptyStackException();
        }
    }

    @Override
    public void push(T value) {
        this.stack.add(0, value);
    }

    @Override
    public T top() throws EmptyStackException {
        try {
            return this.stack.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new EmptyStackException();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int getSize() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }
}
