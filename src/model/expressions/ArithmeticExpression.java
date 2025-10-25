package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.expressions.exceptions.DivisionByZeroException;
import model.expressions.exceptions.ExpressionException;
import model.expressions.exceptions.IncorrectOperandTypeException;
import model.expressions.exceptions.IncorrectOperatorException;
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
    public Value evaluate(IADTDictionary<String, Value> symbolsTable) throws ExpressionException {
        Value valueLeft, valueRight;

        valueLeft = this.expressionLeft.evaluate(symbolsTable);
        if (!(valueLeft.getType() instanceof IntType)) throw new IncorrectOperandTypeException("First operand is not an integer.");

        valueRight = this.expressionRight.evaluate(symbolsTable);
        if (!(valueRight.getType() instanceof IntType)) throw new IncorrectOperandTypeException("Second operand is not an integer.");

        int numberLeft = ((IntValue) valueLeft).getValue();
        int numberRight = ((IntValue) valueRight).getValue();

        switch (this.operator) {
            case '+':
                return new IntValue(numberLeft + numberRight);
            case '-':
                return new IntValue(numberLeft - numberRight);
            case '*':
                return new IntValue(numberLeft * numberRight);
            case '/':
                if (numberRight == 0) throw new DivisionByZeroException();
                return new IntValue(numberLeft / numberRight);
            default:
                throw new IncorrectOperatorException(String.valueOf(this.operator));
        }
    }

    @Override
    public String toString() {
        return this.expressionLeft + " " + this.operator + " " + this.expressionRight;
    }
}
