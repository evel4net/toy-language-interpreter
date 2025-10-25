package model.statements;

import model.expressions.Expression;
import model.exceptions.ProgramException;
import model.exceptions.InvalidTypeException;
import model.exceptions.VariableNotDefinedException;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements Statement {
    private final String name;
    private final Expression expression;

    public AssignmentStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.name)) throw new VariableNotDefinedException(this.name);

        Value expressionValue = this.expression.evaluate(symbolsTable);

        Type variableType = symbolsTable.getVariableType(this.name);
        if (!(expressionValue.getType().equals(variableType))) throw new InvalidTypeException("Assignment type mismatch with variable type.");

        symbolsTable.updateVariableValue(this.name, expressionValue);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(this.name, this.expression);
    }

    @Override
    public String toString() {
        return this.name + " = " + this.expression.toString();
    }
}
