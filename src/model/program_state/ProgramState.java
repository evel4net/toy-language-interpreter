package model.program_state;

import model.statements.Statement;

public class ProgramState {
    private ExecutionStack executionStack;
    private SymbolsTable symbolsTable;
    private Output output;
    private FileTable fileTable;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack executionStack,
                        SymbolsTable symbolsTable,
                        Output output,
                        FileTable fileTable,
                        Statement program) {

        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.output = output;
        this.fileTable = fileTable;

        this.originalProgram = program.deepCopy();

        this.executionStack.push(program);
    }

    public ExecutionStack getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(ExecutionStack executionStack) {
        this.executionStack = executionStack;
    }

    public SymbolsTable getSymbolsTable() {
        return this.symbolsTable;
    }

    public void setSymbolsTable(SymbolsTable symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public Output getOutput() {
        return this.output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "executionStack=" + this.executionStack.toString() +
                ", symbolsTable=" + this.symbolsTable.toString() +
                ", fileTable=" + this.fileTable.toString() +
                ", output=" + this.output.toString() +
                '}';
    }
}
