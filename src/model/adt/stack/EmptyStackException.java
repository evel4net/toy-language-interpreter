package model.adt.stack;

public class EmptyStackException extends Exception {
    public EmptyStackException() {
        super("Operation not valid, stack is empty.");
    }
}
