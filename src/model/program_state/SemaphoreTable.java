package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;
import model.values.Value;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SemaphoreTable {
    private Map<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = new ConcurrentHashMap<>();
    private int newFreeAddress = 1;

    public synchronized int addNewSemaphore(int value) throws KeyAlreadyExistsException {
        int entryAddress = this.newFreeAddress;
        if (this.existsAddress(entryAddress)) throw new KeyAlreadyExistsException("Semaphore table address " + entryAddress + " is already associated to a value.");

        this.semaphoreTable.put(entryAddress, new Pair<>(value, new ArrayList<>()));
        this.newFreeAddress += 1;

        return entryAddress;
    }

    public synchronized Pair<Integer, ArrayList<Integer>> getPair(int address) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        return this.semaphoreTable.get(address);
    }

    public synchronized boolean existsAddress(int address) {
        return this.semaphoreTable.containsKey(address);
    }

    public synchronized Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent() {
        return Map.copyOf(this.semaphoreTable);
    }

    public synchronized void updateEntry(int address, Pair<Integer, ArrayList<Integer>> newPair) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        this.semaphoreTable.put(address, newPair);
    }

    @Override
    public String toString() {
        return this.semaphoreTable.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (int address : this.semaphoreTable.keySet()) {
            logFileEntry += Integer.toString(address) + " : " + this.semaphoreTable.get(address).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
