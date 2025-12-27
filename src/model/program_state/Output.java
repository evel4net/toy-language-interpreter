package model.program_state;

import model.adt.list.ADTSynchronizedList;
import model.adt.list.IADTList;
import model.values.Value;

import java.util.List;

public class Output {
    private final IADTList<Value> list = new ADTSynchronizedList<>(); // thread-safe list

    public void add(Value value) {
        this.list.insertLast(value);
    }

    public List<Value> getContent() {
        return this.list.getAll();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    public String toLogFileString() {
        String logFileEntry = "";

        for (Value value : this.list.getAll()) {
            logFileEntry += value.toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
