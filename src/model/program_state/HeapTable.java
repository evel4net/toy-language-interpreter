package model.program_state;

import model.adt.dictionary.KeyNotDefinedException;
import model.values.Value;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HeapTable {
    private Map<Integer, Value> heap = new ConcurrentHashMap<>();
    private int newFreeAddress = 1;

    public int addNewEntry(Value value) throws KeyAlreadyExistsException {
        int entryAddress = this.newFreeAddress;
        if (this.existsAddress(entryAddress)) throw new KeyAlreadyExistsException("Heap address " + entryAddress + " is already associated to a value.");

        this.heap.put(entryAddress, value);
        this.newFreeAddress += 1;

        return entryAddress;
    }

    public void updateEntry(int address, Value value) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        this.heap.put(address, value);
    }

    public Value getValue(int address) throws KeyNotDefinedException {
        if (!this.existsAddress(address)) throw new KeyNotDefinedException(Integer.toString(address));

        return this.heap.get(address);
    }

    public boolean existsAddress(int address) {
        return this.heap.containsKey(address);
    }

    public Map<Integer, Value> getContent() {
        return Map.copyOf(this.heap);
    }

    public void setContent(Map<Integer, Value> content) {
        this.heap = content;
    }

    public List<Value> getValues() {
        return this.heap.values().stream().toList();
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (int address : this.heap.keySet()) {
            logFileEntry += Integer.toString(address) + " : " + this.heap.get(address).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
