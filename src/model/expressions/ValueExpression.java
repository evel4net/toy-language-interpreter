package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(IADTDictionary<String, Value> symbolsTable) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
