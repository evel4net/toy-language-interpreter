package model.program_state;

import model.adt.Tuple;
import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;

public interface ISemaphoreTable {
    int addNewSemaphore(int value1, int value2) throws KeyAlreadyExistsException;
    Tuple<Integer, ArrayList<Integer>, Integer> getTuple(int address) throws KeyNotDefinedException;
    boolean existsAddress(int address);
    void updateEntry(int address, Tuple<Integer, ArrayList<Integer>, Integer> newTuple) throws KeyNotDefinedException;

    String toString();
    String tologFileString();
}
