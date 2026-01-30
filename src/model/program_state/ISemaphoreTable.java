package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;

public interface ISemaphoreTable {
    int addNewSemaphore(int value) throws KeyAlreadyExistsException;
    Pair<Integer, ArrayList<Integer>> getPair(int address) throws KeyNotDefinedException;
    boolean existsAddress(int address);
    Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent();
    void updateEntry(int address, Pair<Integer, ArrayList<Integer>> newPair) throws KeyNotDefinedException;

    String toString();
    String tologFileString();
}
