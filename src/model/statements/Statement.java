package model.statements;

import model.expressions.exceptions.ExpressionException;
import model.program_state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws ExpressionException, StatementException;
    Statement deepCopy();
    String toString();
}
