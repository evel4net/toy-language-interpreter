package model.statements.cyclic_barrier_operations;

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

public class NewBarrierStatement implements Statement {
    private final String variable;
    private final Expression expression;

    public NewBarrierStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeapTable());

        if (!expressionValue.getType().equals(new IntType())) throw new InvalidTypeException("Barrier expression is not of integer type");

        int numberBarrierThreads = ((IntValue)expressionValue).getValue();
        int newBarrierIndex = state.getBarrierTable().addNewBarrier(numberBarrierThreads);

        if (symbolsTable.isVariableDefined(this.variable)) {
            if (symbolsTable.getVariableType(this.variable) instanceof IntType) {
                symbolsTable.updateVariableValue(this.variable, new IntValue(newBarrierIndex));

            } else throw new InvalidTypeException("Barrier variable is not of int type.");
        } else throw new VariableNotDefinedException(this.variable);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("Barrier variable is not of int type.");
        if (!typeExpression.equals(new IntType())) throw new InvalidTypeException("Barrier expression is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new NewBarrierStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "NewBarrier(" + this.variable + ", " + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString();
    }
}
