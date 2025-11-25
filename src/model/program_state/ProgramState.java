package model.program_state;

import model.adt.stack.EmptyStackException;
import model.statements.Statement;

public class ProgramState {
    private static int availableId = 0;

    private int id;
    private ExecutionStack executionStack;
    private SymbolsTable symbolsTable;
    private Output output;
    private FileTable fileTable;
    private HeapTable heapTable;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack executionStack,
                        SymbolsTable symbolsTable,
                        Output output,
                        FileTable fileTable,
                        HeapTable heapTable,
                        Statement program) {
        this.id = this.getNextId();

        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;

        this.originalProgram = program.deepCopy();

        this.executionStack.push(program);
    }

    private synchronized int getNextId() {
        ProgramState.availableId++;
        return ProgramState.availableId;
    }

    public int getId() { return this.id; }

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

    public HeapTable getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(HeapTable heapTable) {
        this.heapTable = heapTable;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    public void resetToOriginalProgram() {
        this.executionStack = new ExecutionStack();
        this.output = new Output();
        this.symbolsTable = new SymbolsTable();
        this.fileTable = new FileTable();
        this.heapTable = new HeapTable();

        Statement program = this.originalProgram.deepCopy();

        this.executionStack.push(program);
    }

    public Boolean isNotCompleted() {
        return (!this.executionStack.isEmpty());
    }

    public ProgramState executeStep() throws EmptyStackException {
        if (this.executionStack.isEmpty()) throw new EmptyStackException();

        Statement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return Integer.toString(this.id) +  ": ProgramState{" +
                "executionStack=" + this.executionStack.toString() +
                ", symbolsTable=" + this.symbolsTable.toString() +
                ", fileTable=" + this.fileTable.toString() +
                ", heapTable=" + this.heapTable.toString() +
                ", output=" + this.output.toString() +
                '}';
    }
}
