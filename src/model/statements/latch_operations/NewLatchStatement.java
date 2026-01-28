package model.statements.latch_operations;

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

public class NewLatchStatement implements Statement {
    private final String variable;
    private final Expression expression;

    public NewLatchStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeapTable());
        if (!(expressionValue.getType() instanceof IntType)) throw new InvalidTypeException("CountDownLatch expression is not of int type.");

        int latchCounter = ((IntValue)expressionValue).getValue();
        int latchAddress = state.getLatchTable().addNewLatch(latchCounter);

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if (!(symbolsTable.getVariableType(this.variable) instanceof IntType)) throw new InvalidTypeException("CountDownLatch variable is not of int type.");

        symbolsTable.updateVariableValue(this.variable, new IntValue(latchAddress));

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("New latch statement variable is not of int type.");
        if (!typeExpression.equals(new IntType())) throw new InvalidTypeException("New latch statement expression is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NewLatchStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "NewLatch(" + this.variable +", " + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
