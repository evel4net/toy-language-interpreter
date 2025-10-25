package model.adt.dictionary;

public class KeyNotDefinedException extends RuntimeException {
    public KeyNotDefinedException(String key) {
        super("Key " + key + " is not defined.");
    }
}
