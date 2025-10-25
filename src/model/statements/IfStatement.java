package model.statements;

import model.expressions.Expression;
import model.expressions.exceptions.ExpressionException;
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
    public ProgramState execute(ProgramState state) throws ExpressionException, StatementException {
        Value expressionValue = this.expression.evaluate(state.getSymbolsTable());

        if (expressionValue instanceof BoolValue expressionValue_Bool) {
            Statement finalStatement = expressionValue_Bool.getValue() ? this.thenStatement : this.elseStatement;
            state.getExecutionStack().push(finalStatement);
        } else {
            throw new StatementException("If statement condition is not a boolean.");
        }

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression, this.thenStatement, this.elseStatement);
    }

    @Override
    public String toString() {
        return "(IF (" + this.expression.toString() + ") THEN (" + this.thenStatement.toString() +
                ") ELSE (" + this.elseStatement.toString() + "))";
    }
}
