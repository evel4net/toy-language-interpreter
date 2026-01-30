package model.program_state;

import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;

public interface ILatchTable {
    int addNewLatch(int value) throws KeyAlreadyExistsException;
    boolean existsAddress(int address);
    int getLatch(int address) throws KeyNotDefinedException;
    void decrementLatch(int address) throws KeyNotDefinedException;
    Map<Integer, Integer> getContent();

    String toString();
    String tologFileString();
}
