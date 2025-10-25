package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.adt.dictionary.KeyNotDefinedException;
import model.expressions.exceptions.ExpressionException;
import model.expressions.exceptions.VariableNotDefinedException;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate(IADTDictionary<String, Value> symbolsTable) throws ExpressionException {
        try {
            return symbolsTable.get(this.name);
        } catch (KeyNotDefinedException e) {
            throw new VariableNotDefinedException(this.name);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
