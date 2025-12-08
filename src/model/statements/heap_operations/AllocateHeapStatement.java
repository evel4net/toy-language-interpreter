package model.statements.heap_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class AllocateHeapStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    public AllocateHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        if(!(symbolsTable.getVariableType(this.variableName) instanceof ReferenceType)) throw new InvalidTypeException("Variable is not of reference type.");

        Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeapTable());
        if (!(expressionValue.getType().equals(((ReferenceType) symbolsTable.getVariableType(this.variableName)).getInnerType()))) throw new InvalidTypeException("Variable and expression type mismatch.");

        int entryAddress = state.getHeapTable().addNewEntry(expressionValue);
        symbolsTable.updateVariableValue(this.variableName, new ReferenceValue(entryAddress, expressionValue.getType()));

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (typeVariable.equals(new ReferenceType(typeExpression)))return typeEnvironment;
        else throw new InvalidTypeException("Allocation expression type mismatch with variable type.");
    }

    @Override
    public Statement deepCopy() {
        return new AllocateHeapStatement(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "new(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
