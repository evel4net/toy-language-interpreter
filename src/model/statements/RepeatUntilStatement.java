package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.types.Type;

public class RepeatUntilStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return null;
    }

    @Override
    public String toPrettyString() {
        return "";
    }
}
