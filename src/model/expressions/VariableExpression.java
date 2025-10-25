package model.expressions;

import model.exceptions.ProgramException;
import model.exceptions.VariableNotDefinedException;
import model.program_state.SymbolsTable;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable) throws ProgramException {
        if (!symbolsTable.isVariableDefined(this.name)) throw new VariableNotDefinedException(this.name);

        return symbolsTable.getVariableValue(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
