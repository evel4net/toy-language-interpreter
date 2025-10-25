package model.statements;

import model.adt.stack.IADTStack;
import model.program_state.ProgramState;

public class CompoundStatement implements Statement {
    private final Statement firstStatement, secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IADTStack<Statement> executionStack = state.getExecutionStack();

        executionStack.push(this.secondStatement);
        executionStack.push(this.firstStatement);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(this.firstStatement, this.secondStatement);
    }

    @Override
    public String toString() {
        return "(" + this.firstStatement.toString() +  ";" + this.secondStatement.toString() + ")";
    }
}
