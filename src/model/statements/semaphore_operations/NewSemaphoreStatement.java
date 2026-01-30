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

public class NewSemaphoreStatement implements Statement {
    private final String variable;
    private final Expression expression1, expression2;

    public NewSemaphoreStatement(String variable, Expression expression1, Expression expression2) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        Value expression1Value = this.expression1.evaluate(symbolsTable, state.getHeapTable());
        Value expression2Value = this.expression2.evaluate(symbolsTable, state.getHeapTable());

        if (!(expression1Value instanceof IntValue)) throw new InvalidTypeException("Semaphore first expression is not of int type.");
        if (!(expression2Value instanceof IntValue)) throw new InvalidTypeException("Semaphore second expression is not of int type.");

        int semaphoreAddress = state.getSemaphoreTable().addNewSemaphore(((IntValue)expression1Value).getValue(), ((IntValue)expression2Value).getValue());

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if(!symbolsTable.getVariableType(this.variable).equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        symbolsTable.updateVariableValue(this.variable, new IntValue(semaphoreAddress));

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression1 = this.expression1.typeCheck(typeEnvironment);
        Type typeExpression2 = this.expression1.typeCheck(typeEnvironment);

        Type typeVariable = typeEnvironment.get(this.variable);

        if (!typeExpression1.equals(new IntType())) throw new InvalidTypeException("Semaphore first expression is not of int type.");
        if (!typeExpression2.equals(new IntType())) throw new InvalidTypeException("Semaphore second expression is not of int type.");
        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NewSemaphoreStatement(this.variable, this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public String toString() {
        return "NewSemaphore(" + this.variable + ", " + this.expression1.toString() + ", " + this.expression2.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}