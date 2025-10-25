package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.program_state.SymbolsTable;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
