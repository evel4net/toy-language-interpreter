package model.adt.list;

public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException(int index) {
        super("Index " + index + " is not valid.");
    }
}
