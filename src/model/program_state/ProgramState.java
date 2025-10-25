package model.program_state;

import model.adt.dictionary.IADTDictionary;
import model.adt.list.IADTList;
import model.adt.stack.IADTStack;
import model.statements.Statement;
import model.values.Value;

public class ProgramState {
    private IADTStack<Statement> executionStack;
    private IADTDictionary<String, Value> symbolsTable;
    private IADTList<Value> output;
    private final Statement originalProgram;

    public ProgramState(IADTStack<Statement> executionStack,
                        IADTDictionary<String, Value> symbolsTable,
                        IADTList<Value> output,
                        Statement program) {

        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.output = output;

        this.originalProgram = program.deepCopy();

        this.executionStack.push(program);
    }

    public IADTStack<Statement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IADTStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public IADTDictionary<String, Value> getSymbolsTable() {
        return this.symbolsTable;
    }

    public void setSymbolsTable(IADTDictionary<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public IADTList<Value> getOutput() {
        return this.output;
    }

    public void setOutput(IADTList<Value> output) {
        this.output = output;
    }

    public Statement getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "executionStack=" + this.executionStack.toString() +
                ", symbolsTable=" + this.symbolsTable.toString() +
                ", output=" + this.output.toString() +
                '}';
    }
}
