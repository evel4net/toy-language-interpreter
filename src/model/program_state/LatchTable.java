package model.program_state;


import model.adt.dictionary.KeyNotDefinedException;
import model.values.Value;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LatchTable {
    private Map<Integer, Integer> latchTable = new ConcurrentHashMap<>();
    private int newFreeAddress = 1;

    public synchronized int addNewLatch(int value) throws KeyAlreadyExistsException {
        int entryAddress = this.newFreeAddress;
        if (this.existsAddress(entryAddress)) throw new KeyAlreadyExistsException("Latch Table address " + entryAddress + " is already associated to a value.");

        this.latchTable.put(entryAddress, value);
        this.newFreeAddress += 1;

        return entryAddress;
    }

    public synchronized boolean existsAddress(int address) {
        return this.latchTable.containsKey(address);
    }

    public synchronized int getLatch(int address) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        return this.latchTable.get(address);
    }

    public synchronized void decrementLatch(int address) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        int currentValue = this.latchTable.get(address);
        this.latchTable.put(address, currentValue - 1);
    }

    public synchronized Map<Integer, Integer> getContent() {
        return Map.copyOf(this.latchTable);
    }

    @Override
    public String toString() {
        return this.latchTable.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (int address : this.latchTable.keySet()) {
            logFileEntry += Integer.toString(address) + " : " + this.latchTable.get(address).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
