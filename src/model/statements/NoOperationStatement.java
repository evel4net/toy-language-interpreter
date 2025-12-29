package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.types.Type;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public String toPrettyString() {
        return "NOP;";
    }
}
