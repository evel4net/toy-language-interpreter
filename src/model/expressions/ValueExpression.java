package model.expressions;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.Type;
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
    public Type typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return this.value.getType();
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
