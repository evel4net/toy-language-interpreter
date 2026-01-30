package model.program_state;

import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;

public interface ILockTable {
    int addNewEntry() throws KeyAlreadyExistsException;
    boolean existsAddress(int address);
    void updateEntry(int address, int value) throws KeyNotDefinedException;
    int getValue(int address) throws KeyNotDefinedException;
    Map<Integer, Integer> getContent();

    String toString();
    String tologFileString();
}
