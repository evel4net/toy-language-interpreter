package model.statements;

import exceptions.ProgramException;
import exceptions.VariableAlreadyDefinedException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.Type;

public class VariableDeclarationStatement implements Statement {
    private final Type type;
    private final String variableName;

    public VariableDeclarationStatement(Type type, String variableName) {
        this.type = type;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (symbolsTable.isVariableDefined(this.variableName)) throw new VariableAlreadyDefinedException(this.variableName);

        symbolsTable.declareVariable(this.variableName, this.type);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        typeEnvironment.insert(this.variableName, this.type);

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(this.type.deepCopy(), this.variableName);
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.variableName;
    }
}
