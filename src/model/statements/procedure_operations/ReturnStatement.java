package model.statements.procedure_operations;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.statements.Statement;
import model.types.Type;

public class ReturnStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        state.popSymbolsTable();
        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public String toString() {
        return "Return";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
