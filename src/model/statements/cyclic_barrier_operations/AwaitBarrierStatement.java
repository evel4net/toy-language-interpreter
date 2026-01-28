package model.statements.cyclic_barrier_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import javafx.util.Pair;
import model.adt.dictionary.IADTDictionary;
import model.program_state.BarrierTable;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

import java.util.ArrayList;

public class AwaitBarrierStatement implements Statement {
    private final String variable;

    public AwaitBarrierStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isVariableDefined(this.variable)) throw new VariableNotDefinedException(this.variable);
        if (!(symbolsTable.getVariableType(this.variable) instanceof IntType)) throw new InvalidTypeException("Variable of barrier to await is not of int type.");

        BarrierTable barrierTable = state.getBarrierTable();

        int barrierIndex = ((IntValue)symbolsTable.getVariableValue(this.variable)).getValue();
        if (!barrierTable.existsIndex(barrierIndex)) throw new ProgramException("Barrier not found.");

        Pair<Integer, ArrayList<Integer>> barrierContent = barrierTable.getBarrierData(barrierIndex);
        int totalNumberThreads = barrierContent.getKey();
        ArrayList<Integer> waitingThreadsList = barrierContent.getValue();
        int numberCurrentlyWaitingThreads = waitingThreadsList.size();

        if (totalNumberThreads > numberCurrentlyWaitingThreads) {
            if (!waitingThreadsList.contains(state.getId())) {
                barrierTable.addNewWaitingThread(barrierIndex, state.getId());
            }
            state.getExecutionStack().push(this);
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeVariable = typeEnvironment.get(this.variable);

        if (typeVariable.equals(new IntType())) return typeEnvironment;
        else throw new InvalidTypeException("Await variable not of int type.");
    }

    @Override
    public Statement deepCopy() {
        return new AwaitBarrierStatement(this.variable);
    }

    @Override
    public String toString() {
        return "Await(" + this.variable + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString();
    }
}
