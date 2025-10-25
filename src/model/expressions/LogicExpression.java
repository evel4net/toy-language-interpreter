package model.expressions;

import model.exceptions.ProgramException;
import model.exceptions.InvalidTypeException;
import model.exceptions.IncorrectOperatorException;
import model.program_state.SymbolsTable;
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
    public Value evaluate(SymbolsTable symbolsTable) throws ProgramException {
        Value valueLeft, valueRight;

        valueLeft = this.expressionLeft.evaluate(symbolsTable);
        if (!(valueLeft.getType() instanceof BoolType)) throw new InvalidTypeException("First operand is not a boolean.");

        valueRight = this.expressionRight.evaluate(symbolsTable);
        if (!(valueRight.getType() instanceof BoolType)) throw new InvalidTypeException("Second operand is not a boolean.");

        boolean boolLeft = ((BoolValue)valueLeft).getValue();
        boolean boolRight = ((BoolValue)valueRight).getValue();

        boolean result = switch (this.operator.toLowerCase(Locale.ROOT)) {
            case "and" -> (boolLeft && boolRight);
            case "or" -> (boolLeft || boolRight);
            default -> throw new IncorrectOperatorException(this.operator);
        };

        return new BoolValue(result);
    }

    @Override
    public  String toString() {
        return this.expressionLeft + this.operator + this.expressionRight;
    }
}
