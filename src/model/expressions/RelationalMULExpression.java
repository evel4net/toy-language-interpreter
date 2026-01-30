package model.expressions;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class RelationalMULExpression implements Expression {
    private final Expression expression1, expression2;

    public RelationalMULExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException {
        Value value1, value2;

        value1 = this.expression1.evaluate(symbolsTable, heapTable);
        if (!(value1.getType() instanceof IntType)) throw new InvalidTypeException("First expression operand is not of integer type.");

        value2 = this.expression2.evaluate(symbolsTable, heapTable);
        if (!(value2.getType() instanceof IntType)) throw new InvalidTypeException("Second expression operand is not of integer type.");

        int number1 = ((IntValue) value1).getValue();
        int number2 = ((IntValue) value2).getValue();

        return new IntValue((number1 * number2) - (number1 + number2));
    }

    @Override
    public Type typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression1 = this.expression1.typeCheck(typeEnvironment);
        Type typeExpression2 = this.expression2.typeCheck(typeEnvironment);

        if (typeExpression1.equals(new IntType())) {
            if (typeExpression2.equals(new IntType())) return new IntType();
            else throw new InvalidTypeException("First expression operand is not of integer type.");
        } else throw new InvalidTypeException("Second expression operand is not of integer type.");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalMULExpression(this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public String toString() {
        return "MUL(" + this.expression1.toString() + ", " + this.expression2.toString() + ")";
    }
}
