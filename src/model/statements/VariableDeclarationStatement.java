package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.types.Type;
import model.values.Value;

public class VariableDeclarationStatement implements Statement {
    private final Type type;
    private final String name;

    public VariableDeclarationStatement(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IADTDictionary<String, Value> symbolsTable = state.getSymbolsTable();

        if (symbolsTable.exists(this.name)) throw new StatementException("Variable " + this.name + " was already declared.");

        symbolsTable.insert(this.name, this.type.getDefaultValue());

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
