package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.expressions.exceptions.DivisionByZeroException;
import model.expressions.exceptions.ExpressionException;
import model.expressions.exceptions.IncorrectOperandTypeException;
import model.expressions.exceptions.IncorrectOperatorException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

import java.util.Locale;

public class LogicExpression implements Expression {
    private final Expression expressionLeft, expressionRight;
    private final String operator;

    public LogicExpression(Expression expressionLeft, Expression expressionRight, String operator) {
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IADTDictionary<String, Value> symbolsTable) throws ExpressionException {
        Value valueLeft, valueRight;

        try {
            valueLeft = this.expressionLeft.evaluate(symbolsTable);
        } catch (DivisionByZeroException e) {
            throw new IncorrectOperandTypeException("First operand is not a boolean.");
        }
        if (!(valueLeft.getType() instanceof BoolType)) throw new IncorrectOperandTypeException("First operand is not a boolean.");

        try {
            valueRight = this.expressionRight.evaluate(symbolsTable);
        } catch (DivisionByZeroException e) {
            throw new IncorrectOperandTypeException("Second operand is not a boolean.");
        }
        if (!(valueRight.getType() instanceof BoolType)) throw new IncorrectOperandTypeException("Second operand is not a boolean.");

        boolean boolLeft = ((BoolValue)valueLeft).getValue();
        boolean boolRight = ((BoolValue)valueRight).getValue();

        switch (this.operator.toLowerCase(Locale.ROOT)) {
            case "and":
                return new BoolValue(boolLeft && boolRight);
            case "or":
                return new BoolValue(boolLeft || boolRight);
            default:
                throw new IncorrectOperatorException(this.operator);
        }
    }

    @Override
    public  String toString() {
        return this.expressionLeft + this.operator + this.expressionRight;
    }
}
