package model.expressions;

import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import exceptions.IncorrectOperatorException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.BoolType;
import model.types.Type;
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
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException {
        Value valueLeft, valueRight;

        valueLeft = this.expressionLeft.evaluate(symbolsTable, heapTable);
        if (!(valueLeft.getType() instanceof BoolType)) throw new InvalidTypeException("Left operand is not of boolean type.");

        valueRight = this.expressionRight.evaluate(symbolsTable, heapTable);
        if (!(valueRight.getType() instanceof BoolType)) throw new InvalidTypeException("Right operand is not of boolean type.");

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
    public Type typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpressionLeft = this.expressionLeft.typeCheck(typeEnvironment);
        Type typeExpressionRight = this.expressionRight.typeCheck(typeEnvironment);

        if (typeExpressionLeft.equals(new BoolType())) {
            if (typeExpressionRight.equals(new BoolType())) return new BoolType();
            else throw new InvalidTypeException("Right operand is not of boolean type.");
        } else throw new InvalidTypeException("Left operand is not of boolean type.");
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(this.expressionLeft.deepCopy(), this.expressionRight.deepCopy(), this.operator);
    }

    @Override
    public  String toString() {
        return this.expressionLeft + this.operator + this.expressionRight;
    }
}
