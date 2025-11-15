package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.expressions.Expression;
import model.program_state.ExecutionStack;
import model.program_state.ProgramState;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements Statement {
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value expressionValue = this.expression.evaluate(state.getSymbolsTable(), );

        if (!(expressionValue instanceof BoolValue)) throw new InvalidTypeException("While expression is not of boolean type.");

        if (((BoolValue) expressionValue).getValue()) {
            ExecutionStack executionStack = state.getExecutionStack();

            executionStack.push(this.statement.deepCopy());
            executionStack.push(this);
        }

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While(" + this.expression.toString() + ") (" + this.statement.toString() + ")";
    }
}
