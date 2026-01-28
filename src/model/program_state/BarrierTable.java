package model.program_state;


import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BarrierTable {
    private Map<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable = new ConcurrentHashMap<>();
    private int newIndex = 1;

    public synchronized int addNewBarrier(int numberThreads) throws KeyAlreadyExistsException {
        int barrierIndex = this.newIndex;
        if (this.existsIndex(barrierIndex)) throw new KeyAlreadyExistsException("Barrier index " + barrierIndex + " is already associated to a barrier.");

        this.barrierTable.put(barrierIndex, new Pair<>(numberThreads, new ArrayList<Integer>()));
        this.newIndex += 1;

        return barrierIndex;
    }

    public synchronized void addNewWaitingThread(int barrierIndex, int threadId) {
        if (!this.existsIndex(barrierIndex)) throw new KeyNotDefinedException(Integer.toString(barrierIndex));

        Pair<Integer, ArrayList<Integer>> currentBarrierData = this.getBarrierData(barrierIndex);
        currentBarrierData.getValue().add(threadId);

        this.barrierTable.put(barrierIndex, new Pair<>(currentBarrierData.getKey(), currentBarrierData.getValue()));
    }

    public synchronized Pair<Integer, ArrayList<Integer>> getBarrierData(int barrierIndex) throws KeyNotDefinedException {
        if (!this.existsIndex(barrierIndex)) throw new KeyNotDefinedException(Integer.toString(barrierIndex));

        return this.barrierTable.get(barrierIndex);
    }

    public synchronized Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent() {
        return Map.copyOf(this.barrierTable);
    }

    public synchronized boolean existsIndex(int index) {
        return this.barrierTable.containsKey(index);
    }

    @Override
    public String toString() {
        return this.barrierTable.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (int index : this.barrierTable.keySet()) {
            logFileEntry += Integer.toString(index) + " : " + this.barrierTable.get(index).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
