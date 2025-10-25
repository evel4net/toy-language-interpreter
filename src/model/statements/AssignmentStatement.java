package model.statements;

import model.adt.dictionary.IADTDictionary;
import model.adt.dictionary.KeyNotDefinedException;
import model.expressions.Expression;
import model.expressions.exceptions.ExpressionException;
import model.program_state.ProgramState;
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
    public ProgramState execute(ProgramState state) throws ExpressionException, StatementException {
        IADTDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        Value expressionValue = this.expression.evaluate(symbolsTable);

        try {
            Type variableType = symbolsTable.get(this.name).getType();
            if (!(expressionValue.getType().equals(variableType)))
                throw new StatementException("Assignment type mismatch with variable type.");

            symbolsTable.update(this.name, expressionValue);
        } catch (KeyNotDefinedException e) {
            throw new StatementException("Variable " + this.name + " is not declared.");
        }

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
