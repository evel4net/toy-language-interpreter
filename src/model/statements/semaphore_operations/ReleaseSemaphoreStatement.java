package model.statements.semaphore_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import javafx.util.Pair;
import model.adt.Tuple;
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

        Tuple<Integer, ArrayList<Integer>, Integer> semaphoreContent = semaphoreTable.getTuple(semaphoreAddress);
        int value1 = semaphoreContent.first;
        ArrayList<Integer> currentThreads = semaphoreContent.second;
        int value2 = semaphoreContent.third;

        if (currentThreads.contains(state.getId())) {
            currentThreads.remove((Object)state.getId());
            semaphoreTable.updateEntry(semaphoreAddress, new Tuple<>(value1, currentThreads, value2));
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