package model.program_state;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.dictionary.KeyNotDefinedException;
import model.statements.Statement;
import model.types.Type;
import model.values.Value;

import java.util.Map;

public class SymbolsTable {
    private final IADTDictionary<String, Value> table = new ADTDictionary<>();

    public void declareVariable(String name, Type type) {
        this.table.insert(name, type.getDefaultValue());
    }

    public boolean isVariableDefined(String variableName) {
        return this.table.exists(variableName);
    }

    public Value getVariableValue(String variableName) throws KeyNotDefinedException {
        return this.table.get(variableName);
    }

    public Type getVariableType(String variableName) throws KeyNotDefinedException {
        return this.table.get(variableName).getType();
    }

    public void updateVariableValue(String variableName, Value newValue) throws KeyNotDefinedException {
        this.table.update(variableName, newValue);
    }

    @Override
    public String toString() {
        return this.table.toString();
    }

    public String toLogFileString() {
        String logFileEntry = "";
        Map<String, Value> mapCopy = this.table.getMap();

        for (Map.Entry<String, Value> variable : mapCopy.entrySet()) {
            logFileEntry += variable.toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
