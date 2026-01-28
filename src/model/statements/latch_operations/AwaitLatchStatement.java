package model.statements.latch_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.LatchTable;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class AwaitLatchStatement implements Statement {
    private final String variable;

    public AwaitLatchStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if (!symbolsTable.getVariableType(this.variable).equals(new IntType())) throw new InvalidTypeException("Await latch variable is not of int type.");

        int latchAddress = ((IntValue)symbolsTable.getVariableValue(this.variable)).getValue();

        LatchTable latchTable = state.getLatchTable();
        if (!latchTable.existsAddress(latchAddress)) throw new ProgramException("CountDownLatch not found.");

        if (latchTable.getLatch(latchAddress) > 0) {
            state.getExecutionStack().push(this);
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);

        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("New latch statement variable is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new AwaitLatchStatement(this.variable);
    }

    @Override
    public String toString() {
        return "Await(" + this.variable + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
