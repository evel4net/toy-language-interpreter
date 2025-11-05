package model.expressions;

import exceptions.IncorrectOperatorException;
import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.program_state.SymbolsTable;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    private final Expression leftExpression, rightExpression;
    private final String operator;

    public RelationalExpression(Expression leftExpression, Expression rightExpression, String operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable) throws ProgramException {
        Value leftValue = this.leftExpression.evaluate(symbolsTable);
        if (!(leftValue.getType() instanceof IntType)) throw new InvalidTypeException("Left relational expression is not of integer type.");

        Value rightValue = this.rightExpression.evaluate(symbolsTable);
        if (!(rightValue.getType() instanceof IntType)) throw new InvalidTypeException("Right relational expression is not of integer type.");

        int leftNumber = ((IntValue) leftValue).getValue();
        int rightNumber = ((IntValue) rightValue).getValue();

        boolean result = switch (this.operator) {
            case "<" -> leftNumber < rightNumber;
            case "<=" -> leftNumber <= rightNumber;
            case "==" -> leftNumber == rightNumber;
            case "!=" -> leftNumber != rightNumber;
            case ">" -> leftNumber > rightNumber;
            case ">=" -> leftNumber >= rightNumber;
            default -> throw new IncorrectOperatorException(this.operator);
        };

        return new BoolValue(result);
    }

    @Override
    public String toString() {
        return this.leftExpression.toString() + this.operator + this.rightExpression.toString();
    }
}
