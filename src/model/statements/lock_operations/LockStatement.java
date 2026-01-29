package model.statements.lock_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.LockTable;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class LockStatement implements Statement {
    private final String variable;

    public LockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if (!symbolsTable.getVariableType(this.variable).equals(new IntType())) throw new InvalidTypeException("Lock variable is not of int type.");

        int lockAddress = ((IntValue)symbolsTable.getVariableValue(this.variable)).getValue();
        LockTable lockTable = state.getLockTable();

        if (!lockTable.existsAddress(lockAddress)) throw new ProgramException("Lock was not found.");

        int currentLockingState = lockTable.getValue(lockAddress);
        if (currentLockingState == -1) lockTable.updateEntry(lockAddress, state.getId());
        else state.getExecutionStack().push(this);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);

        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("New lock variable is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new LockStatement(this.variable);
    }

    @Override
    public String toString() {
        return "Lock(" + this.variable + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
