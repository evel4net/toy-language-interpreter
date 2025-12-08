package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.types.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws ProgramException;
    IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException;
    Statement deepCopy();
    String toString();
}
