package model.adt.dictionary;

public class KeyNotDefinedException extends Exception {
    public KeyNotDefinedException(String key) {
        super("Key " + key + " is not defined.");
    }
}
