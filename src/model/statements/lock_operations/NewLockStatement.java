package model.statements.lock_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class NewLockStatement implements Statement {
    private final String variable;

    public NewLockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        int lockAddress = state.getLockTable().addNewEntry();

        SymbolsTable symbolsTable = state.getSymbolsTable();
        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);

        symbolsTable.updateVariableValue(this.variable, new IntValue(lockAddress));

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
        return new NewLockStatement(this.variable);
    }

    @Override
    public String toString() {
        return "NewLock(" + this.variable + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
