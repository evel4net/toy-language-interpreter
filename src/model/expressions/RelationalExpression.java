package model.expressions;

import exceptions.IncorrectOperatorException;
import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    private final Expression expressionLeft, expressionRight;
    private final String operator;

    public RelationalExpression(Expression expressionLeft, Expression expressionRight, String operator) {
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
        this.operator = operator;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException {
        Value leftValue = this.expressionLeft.evaluate(symbolsTable, heapTable);
        if (!(leftValue.getType() instanceof IntType)) throw new InvalidTypeException("Left relational expression is not of integer type.");

        Value rightValue = this.expressionRight.evaluate(symbolsTable, heapTable);
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
    public Expression deepCopy() {
        return new RelationalExpression(this.expressionLeft.deepCopy(), this.expressionRight.deepCopy(), this.operator);
    }

    @Override
    public String toString() {
        return this.expressionLeft.toString() + this.operator + this.expressionRight.toString();
    }
}
