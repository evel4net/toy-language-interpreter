package model.adt.stack;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("Operation not valid, stack is empty.");
    }
}
