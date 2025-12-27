package model.program_state;

import model.adt.stack.ADTStack;
import model.adt.stack.EmptyStackException;
import model.adt.stack.IADTStack;
import model.statements.Statement;

import java.util.List;

public class ExecutionStack {
    private final IADTStack<Statement> stack = new ADTStack<>();

    public Statement pop() throws EmptyStackException {
        return this.stack.pop();
    }

    public void push(Statement statement) {
        this.stack.push(statement);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public List<Statement> getContent() {
        return this.stack.getAll();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    public String toLogFileString() {
        String logFileEntry = "";

        for (Statement s : this.stack.getAll()) {
            logFileEntry += s.toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
