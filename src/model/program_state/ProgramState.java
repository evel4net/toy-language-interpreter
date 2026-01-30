package model.program_state;

import model.adt.stack.EmptyStackException;
import model.statements.Statement;

import java.util.Stack;

public class ProgramState {
    private static int availableId = 0;

    private final int id;
    private ExecutionStack executionStack;
    private Stack<SymbolsTable> symbolsTables;
    private Output output;
    private FileTable fileTable;
    private HeapTable heapTable;
    private ProceduresTable proceduresTable;
    private final Statement originalProgram;

    public ProgramState(ExecutionStack executionStack,
                        Stack<SymbolsTable> symbolsTables,
                        Output output,
                        FileTable fileTable,
                        HeapTable heapTable,
                        ProceduresTable proceduresTable,
                        Statement program) {
        this.id = ProgramState.getNextId();

        this.executionStack = executionStack;
        this.symbolsTables = symbolsTables;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.proceduresTable = proceduresTable;

        this.originalProgram = program.deepCopy();

        this.executionStack.push(program);
    }

    private synchronized static int getNextId() {
        ProgramState.availableId++;
        return ProgramState.availableId;
    }

    public void addSymbolsTable(SymbolsTable newSymbolsTable) { this.symbolsTables.push(newSymbolsTable); }

    public SymbolsTable popSymbolsTable() { return this.symbolsTables.pop(); }

    public int getId() { return this.id; }

    public ExecutionStack getExecutionStack() {
        return this.executionStack;
    }

    public SymbolsTable getSymbolsTable() {
        return this.symbolsTables.peek();
    }

    public Stack<SymbolsTable> getAllSymbolsTables() { return symbolsTables; }

    public Output getOutput() {
        return this.output;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public HeapTable getHeapTable() {
        return heapTable;
    }

    public ProceduresTable getProceduresTable() {
        return proceduresTable;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    public void resetToOriginalProgram() {
        this.executionStack = new ExecutionStack();
        this.output = new Output();

        Stack<SymbolsTable> stack = new Stack<>();
        stack.push(new SymbolsTable());
        this.symbolsTables = stack;

        this.fileTable = new FileTable();
        this.heapTable = new HeapTable();
//        this.proceduresTable = new ProceduresTable();

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
                ", symbolsTables=" + this.symbolsTables.toString() +
                ", fileTable=" + this.fileTable.toString() +
                ", heapTable=" + this.heapTable.toString() +
                ", proceduresTable=" + this.proceduresTable.toString() +
                ", output=" + this.output.toString() +
                '}';
    }
}
