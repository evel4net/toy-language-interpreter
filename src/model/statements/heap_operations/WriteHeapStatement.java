package model.statements.heap_operations;

import exceptions.HeapAddressNotAssociated;
import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

public class WriteHeapStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    public WriteHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        if (!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        Value variableValue = symbolsTable.getVariableValue(this.variableName);
        if (!(variableValue.getType() instanceof ReferenceType)) throw new InvalidTypeException("Variable is not of reference type.");

        int variableAddress = ((ReferenceValue) variableValue).getAddress();
        if (!state.getHeapTable().existsAddress(variableAddress)) throw new HeapAddressNotAssociated(variableAddress);

        Value expressionValue = this.expression.evaluate(state.getSymbolsTable(), );
        if (expressionValue.getType().equals(((ReferenceValue) variableValue).getType())) throw new InvalidTypeException("Variable and expression type mismatch.");

        state.getHeapTable().updateEntry(variableAddress, expressionValue);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new WriteHeapStatement(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "WriteHeap(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
