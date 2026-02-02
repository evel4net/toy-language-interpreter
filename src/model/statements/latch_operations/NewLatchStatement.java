package model.statements.latch_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.LatchTable;
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

        if (!expressionValue.getType().equals(new IntType())) throw new InvalidTypeException("Expression of new latch is not of int type.");

        int newLatch = ((IntValue)expressionValue).getValue();
        LatchTable latchTable = state.getLatchTable();
        int newLatchAddress = latchTable.addNewLatch(newLatch);

        if (symbolsTable.isVariableDefined(this.variable) && symbolsTable.getVariableType(this.variable).equals(new IntType())) {
            symbolsTable.updateVariableValue(this.variable, new IntValue(newLatchAddress));
        } else throw new VariableNotDefinedException(this.variable);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        if (!typeEnvironment.get(this.variable).equals(new IntType())) throw new InvalidTypeException("Variable of new latch is not of int type.");

        if (!this.expression.typeCheck(typeEnvironment).equals(new IntType())) throw new InvalidTypeException("Expression of new latch is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NewLatchStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "NewLatch(" + this.variable + ", " + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
