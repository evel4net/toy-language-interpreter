package model.adt.list;

public class InvalidIndexException extends Exception {
    public InvalidIndexException(int index) {
        super("Index " + index + " is not valid.");
    }
}
