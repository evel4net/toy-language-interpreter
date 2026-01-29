package model.program_state;

import model.adt.dictionary.KeyNotDefinedException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockTable {
    private Map<Integer, Integer> lockTable = new ConcurrentHashMap<>();
    private int newFreeAddress = 1;

    public synchronized int addNewEntry() throws KeyAlreadyExistsException {
        int entryAddress = this.newFreeAddress;
        if (this.existsAddress(entryAddress)) throw new KeyAlreadyExistsException("Heap address " + entryAddress + " is already associated to a value.");

        this.lockTable.put(entryAddress, -1);
        this.newFreeAddress += 1;

        return entryAddress;
    }

    public synchronized boolean existsAddress(int address) {
        return this.lockTable.containsKey(address);
    }

    public synchronized void updateEntry(int address, int value) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        this.lockTable.put(address, value);
    }

    public synchronized int getValue(int address) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        return this.lockTable.get(address);
    }

    public synchronized Map<Integer, Integer> getContent() {
        return Map.copyOf(this.lockTable);
    }

    @Override
    public String toString() {
        return this.lockTable.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (int address : this.lockTable.keySet()) {
            logFileEntry += Integer.toString(address) + " : " + this.lockTable.get(address).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
