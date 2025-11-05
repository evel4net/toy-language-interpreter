package model.statements;

import model.expressions.Expression;
import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import model.program_state.ProgramState;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement thenStatement, elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value expressionValue = this.expression.evaluate(state.getSymbolsTable());

        if (!(expressionValue instanceof BoolValue expressionValue_Bool)) throw new InvalidTypeException("If statement condition is not a boolean.");

        Statement finalStatement = expressionValue_Bool.getValue() ? this.thenStatement : this.elseStatement;
        state.getExecutionStack().push(finalStatement);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression, this.thenStatement, this.elseStatement);
    }

    @Override
    public String toString() {
        return "If (" + this.expression.toString() + ") then (" + this.thenStatement.toString() +
                ") else (" + this.elseStatement.toString() + ")";
    }
}
