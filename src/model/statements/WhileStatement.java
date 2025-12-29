package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.ExecutionStack;
import model.program_state.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WhileStatement implements Statement {
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value expressionValue = this.expression.evaluate(state.getSymbolsTable(), state.getHeapTable());

        if (!(expressionValue instanceof BoolValue)) throw new InvalidTypeException("While expression is not of boolean type.");

        if (((BoolValue) expressionValue).getValue()) {
            ExecutionStack executionStack = state.getExecutionStack();

            executionStack.push(this);
            executionStack.push(this.statement.deepCopy());
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (typeExpression.equals(new BoolType())) {
            this.statement.typeCheck(typeEnvironment.deepClone());

            return typeEnvironment;
        }
        else throw new InvalidTypeException("While expression is not of boolean type.");
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While (" + this.expression.toString() + ") (" + this.statement.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        String statementString = Arrays.stream(this.statement.toPrettyString().split("\n"))
                .map(str -> { return "\t" + str; })
                .collect(Collectors.joining("\n"));

        return "While (" + this.expression.toString() + ") (\n" + statementString + "\n);";
    }
}
