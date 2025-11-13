package model.program_state;

import model.values.StringValue;
import model.values.Value;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

public class HeapTable {
    private final Map<Integer, Value> heap = new HashMap<>();
    private int newFreeAddress = 1;

    public int addNewEntry(Value value) throws KeyAlreadyExistsException {
        int entryAddress = this.newFreeAddress;
        if (this.heap.containsKey(entryAddress)) throw new KeyAlreadyExistsException("Heap address " + entryAddress + " is already associated to a value.");

        this.heap.put(entryAddress, value);
        this.newFreeAddress += 1;

        return entryAddress;
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
