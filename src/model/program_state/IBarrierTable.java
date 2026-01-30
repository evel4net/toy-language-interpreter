package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;

public interface IBarrierTable {
    int addNewBarrier(int numberThreads) throws KeyAlreadyExistsException;
    void addNewWaitingThread(int barrierIndex, int threadId);
    Pair<Integer, ArrayList<Integer>> getBarrierData(int barrierIndex) throws KeyNotDefinedException;
    Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent();
    boolean existsIndex(int index);

    String toString();
    String tologFileString();
}
