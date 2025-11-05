package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import exceptions.IncorrectOperatorException;
import model.program_state.SymbolsTable;
import model.types.IntType;
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
    public Value evaluate(SymbolsTable symbolsTable) throws ProgramException {
        Value valueLeft, valueRight;

        valueLeft = this.expressionLeft.evaluate(symbolsTable);
        if (!(valueLeft.getType() instanceof IntType)) throw new InvalidTypeException("First operand is not an integer.");

        valueRight = this.expressionRight.evaluate(symbolsTable);
        if (!(valueRight.getType() instanceof IntType)) throw new InvalidTypeException("Second operand is not an integer.");

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

    private int divideOperation(int numberLeft, int numberRight) throws DivisionByZeroException {
        if (numberRight == 0) throw new DivisionByZeroException();

        return (numberLeft / numberRight);
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(this.expressionLeft, this.expressionRight, this.operator);
    }

    @Override
    public String toString() {
        return this.expressionLeft + " " + this.operator + " " + this.expressionRight;
    }
}
