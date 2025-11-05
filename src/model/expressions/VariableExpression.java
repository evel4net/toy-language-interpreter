package model.expressions;

import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.program_state.SymbolsTable;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable) throws ProgramException {
        if (!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        return symbolsTable.getVariableValue(this.variableName);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(this.variableName);
    }

    @Override
    public String toString() {
        return this.variableName;
    }
}
