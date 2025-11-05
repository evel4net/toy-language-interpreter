package model.statements;

import exceptions.ProgramException;
import model.program_state.ProgramState;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        return state;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
