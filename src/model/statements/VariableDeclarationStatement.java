package model.statements;

import model.exceptions.ProgramException;
import model.exceptions.VariableAlreadyDefinedException;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.Type;

public class VariableDeclarationStatement implements Statement {
    private final Type type;
    private final String name;

    public VariableDeclarationStatement(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (symbolsTable.isVariableDefined(this.name)) throw new VariableAlreadyDefinedException(this.name);

        symbolsTable.declareVariable(this.name, this.type);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(this.type, this.name);
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
