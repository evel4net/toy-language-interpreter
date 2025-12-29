package model.statements;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import exceptions.VariableNotDefinedException;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    public AssignmentStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeapTable());

        Type variableType = symbolsTable.getVariableType(this.variableName);
        if (!(expressionValue.getType().equals(variableType))) throw new InvalidTypeException("Assignment type mismatch with variable type.");

        symbolsTable.updateVariableValue(this.variableName, expressionValue);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (typeExpression.equals(typeVariable)) return typeEnvironment;
        else throw new InvalidTypeException("Assignment type mismatch with variable type.");
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return this.variableName + "=" + this.expression.toString();
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
