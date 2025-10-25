package model.statements;

import model.expressions.Expression;
import model.expressions.exceptions.ExpressionException;
import model.program_state.ProgramState;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException {
        state.getOutput().insertLast(this.expression.evaluate(state.getSymbolsTable()));

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression);
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
