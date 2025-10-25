package model.program_state;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.dictionary.KeyNotDefinedException;
import model.types.Type;
import model.values.Value;

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
}
