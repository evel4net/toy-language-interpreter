package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapAllocationStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        if(!(symbolsTable.getVariableType(this.variableName) instanceof ReferenceType)) throw new InvalidTypeException("Variable is not of reference type.");

        Value result = this.expression.evaluate(symbolsTable);
        if (!(result.getType().equals(symbolsTable.getVariableValue(this.variableName).getType()))) throw new InvalidTypeException("Variable and expression type mismatch.");

        int entryAddress = state.getHeapTable().addNewEntry(result);
        symbolsTable.updateVariableValue(this.variableName, new ReferenceValue(entryAddress, result.getType()));

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new HeapAllocationStatement(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "new(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
