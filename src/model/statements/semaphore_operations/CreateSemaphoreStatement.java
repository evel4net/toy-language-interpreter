package model.statements.semaphore_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class CreateSemaphoreStatement implements Statement {
    private final String variable;
    private final Expression expression;

    public CreateSemaphoreStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeapTable());

        if (!(expressionValue instanceof IntValue)) throw new InvalidTypeException("Semaphore expression is not of int type.");

        int semaphoreAddress = state.getSemaphoreTable().addNewSemaphore(((IntValue)expressionValue).getValue());

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if(!symbolsTable.getVariableType(this.variable).equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        symbolsTable.updateVariableValue(this.variable, new IntValue(semaphoreAddress));

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        Type typeVariable = typeEnvironment.get(this.variable);

        if (!typeExpression.equals(new IntType())) throw new InvalidTypeException("Semaphore expression is not of int type.");
        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new CreateSemaphoreStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "CreateSemaphore(" + this.variable + ", " + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
