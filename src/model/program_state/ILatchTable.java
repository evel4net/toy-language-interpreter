package model.program_state;

import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;

public interface ILatchTable {
    int addNewLatch(Integer value) throws KeyAlreadyExistsException;
    void countDown(int address)  throws KeyNotDefinedException;
    boolean existsAddress(int address);
    Integer getLatch(int address) throws KeyNotDefinedException;
    Map<Integer, Integer> getContent();
    String toString();
    String tologFileString();
}
