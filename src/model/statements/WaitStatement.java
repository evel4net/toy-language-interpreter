package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.ValueExpression;
import model.program_state.ProgramState;
import model.types.Type;
import model.values.IntValue;

public class WaitStatement implements Statement {
    private final int counter;

    public WaitStatement(int counter) {
        this.counter = counter;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        if (this.counter != 0) {
            Statement newStatement = new CompoundStatement(
                    new PrintStatement(new ValueExpression(new IntValue(this.counter))),
                    new WaitStatement(this.counter - 1)
            );

            state.getExecutionStack().push(newStatement);
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new WaitStatement(this.counter);
    }

    @Override
    public String toString() {
        return "Wait(" + this.counter + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
