package model.statements;

import exceptions.ProgramException;
import model.program_state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state) throws ProgramException;
    Statement deepCopy();
    String toString();
}
