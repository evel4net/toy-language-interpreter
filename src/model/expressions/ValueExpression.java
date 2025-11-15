package model.expressions;

import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) {
        return this.value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
