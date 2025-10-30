package model.program_state;

import model.adt.list.ADTList;
import model.adt.list.IADTList;
import model.values.Value;


public class Output {
    private final IADTList<Value> list = new ADTList<>();

    public void add(Value value) {
        this.list.insertLast(value);
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
