package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ExecutionStack;
import model.program_state.ProgramState;
import model.types.Type;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ForkStatement implements Statement {
    private final Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        return new ProgramState(
                new ExecutionStack(),
                state.getSymbolsTable().deepCopy(),
                state.getOutput(),
                state.getFileTable(),
                state.getHeapTable(),
                state.getLatchTable(),
                this.statement);
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return this.statement.typeCheck(typeEnvironment);
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "Fork(" + this.statement.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        String statementString = Arrays.stream(this.statement.toPrettyString().split("\n"))
                .map(str -> { return "\t" + str;})
                .collect(Collectors.joining("\n"));
        return "Fork(\n" + statementString + "\n);";
    }
}
