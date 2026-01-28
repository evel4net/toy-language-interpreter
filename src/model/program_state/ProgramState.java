package model.program_state;

import model.adt.stack.EmptyStackException;
import model.statements.Statement;

public class ProgramState {
    private static int availableId = 0;

    private final int id;
    private ExecutionStack executionStack;
    private SymbolsTable symbolsTable;
    private Output output;
    private FileTable fileTable;
    private HeapTable heapTable;
    private LatchTable latchTable;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack executionStack,
                        SymbolsTable symbolsTable,
                        Output output,
                        FileTable fileTable,
                        HeapTable heapTable,
                        LatchTable latchTable,
                        Statement program) {
        this.id = ProgramState.getNextId();

        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.latchTable = latchTable;

        this.originalProgram = program.deepCopy();

        this.executionStack.push(program);
    }

    private synchronized static int getNextId() {
        ProgramState.availableId++;
        return ProgramState.availableId;
    }

    public int getId() { return this.id; }

    public ExecutionStack getExecutionStack() {
        return this.executionStack;
    }

    public SymbolsTable getSymbolsTable() {
        return this.symbolsTable;
    }

    public Output getOutput() {
        return this.output;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public HeapTable getHeapTable() {
        return heapTable;
    }

    public LatchTable getLatchTable() {
        return latchTable;
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
        this.latchTable = new LatchTable();

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
                ", latchTable=" + this.latchTable.toString() +
                ", output=" + this.output.toString() +
                '}';
    }
}
