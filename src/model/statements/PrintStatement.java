package model.statements;

import model.expressions.Expression;
import exceptions.ProgramException;
import model.program_state.ProgramState;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        state.getOutput().add(this.expression.evaluate(state.getSymbolsTable()));

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression);
    }

    @Override
    public String toString() {
        return "Print(" + this.expression.toString() + ")";
    }
}
