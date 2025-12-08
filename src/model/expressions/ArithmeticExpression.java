package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import exceptions.IncorrectOperatorException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression {
    private final Expression expressionLeft, expressionRight;
    private final char operator;

    public ArithmeticExpression(Expression expressionLeft, Expression expressionRight, char operator) {
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
        this.operator = operator;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException {
        Value valueLeft, valueRight;

        valueLeft = this.expressionLeft.evaluate(symbolsTable, heapTable);
        if (!(valueLeft.getType() instanceof IntType)) throw new InvalidTypeException("Left operand is not of integer type.");

        valueRight = this.expressionRight.evaluate(symbolsTable, heapTable);
        if (!(valueRight.getType() instanceof IntType)) throw new InvalidTypeException("Right operand is not of integer type.");

        int numberLeft = ((IntValue) valueLeft).getValue();
        int numberRight = ((IntValue) valueRight).getValue();

        int result = switch (this.operator) {
            case '+' -> (numberLeft + numberRight);
            case '-' -> (numberLeft - numberRight);
            case '*' -> (numberLeft * numberRight);
            case '/' -> this.divideOperation(numberLeft, numberRight);
            default -> throw new IncorrectOperatorException(String.valueOf(this.operator));
        };

        return new IntValue(result);
    }

    @Override
    public Type typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpressionLeft = this.expressionLeft.typeCheck(typeEnvironment);
        Type typeExpressionRight = this.expressionRight.typeCheck(typeEnvironment);

        if (typeExpressionLeft.equals(new IntType())) {
            if (typeExpressionRight.equals(new IntType())) return new IntType();
            else throw new InvalidTypeException("Right operand is not of integer type.");
        } else throw new InvalidTypeException("Left operand is not of integer type.");
    }

    private int divideOperation(int numberLeft, int numberRight) throws DivisionByZeroException {
        if (numberRight == 0) throw new DivisionByZeroException();

        return (numberLeft / numberRight);
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(this.expressionLeft.deepCopy(), this.expressionRight.deepCopy(), this.operator);
    }

    @Override
    public String toString() {
        return this.expressionLeft + " " + this.operator + " " + this.expressionRight;
    }
}
