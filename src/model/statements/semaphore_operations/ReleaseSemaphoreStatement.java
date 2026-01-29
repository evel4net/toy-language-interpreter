package model.statements.semaphore_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import javafx.util.Pair;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.program_state.SemaphoreTable;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

import java.util.ArrayList;

public class ReleaseSemaphoreStatement implements Statement {
    private final String variable;

    public ReleaseSemaphoreStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if(!symbolsTable.getVariableType(this.variable).equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        int semaphoreAddress = ((IntValue)symbolsTable.getVariableValue(this.variable)).getValue();
        SemaphoreTable semaphoreTable = state.getSemaphoreTable();

        if (!semaphoreTable.existsAddress(semaphoreAddress)) throw new ProgramException("Semaphore was not found.");

        Pair<Integer, ArrayList<Integer>> semaphoreContent = semaphoreTable.getPair(semaphoreAddress);
        ArrayList<Integer> currentThreads = semaphoreContent.getValue();
        int totalNumberThreads = semaphoreContent.getKey();

        if (currentThreads.contains(state.getId())) {
            currentThreads.remove((Object)state.getId());
            semaphoreTable.updateEntry(semaphoreAddress, new Pair<>(totalNumberThreads, currentThreads));
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);
        if (!typeVariable.equals(new IntType())) throw new InvalidTypeException("Semaphore variable is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new ReleaseSemaphoreStatement(this.variable);
    }

    @Override
    public String toString() {
        return "Release(" + this.variable + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
