package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ExecutionStack;
import model.program_state.ProgramState;
import model.types.Type;

public class CompoundStatement implements Statement {
    private final Statement firstStatement, secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack executionStack = state.getExecutionStack();

        executionStack.push(this.secondStatement);
        executionStack.push(this.firstStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.firstStatement.toString() +  ";" + this.secondStatement.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.firstStatement.toPrettyString() + "\n" + this.secondStatement.toPrettyString() + "\n";
    }
}
