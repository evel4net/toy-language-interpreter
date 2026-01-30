package model.statements;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.ValueExpression;
import model.program_state.ProgramState;
import model.types.Type;
import model.values.IntValue;

public class SleepStatement implements Statement {
    private final int counter;

    public SleepStatement(int counter) {
        this.counter = counter;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        if (this.counter != 0) {
            state.getExecutionStack().push(new SleepStatement(this.counter - 1));
        }

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new SleepStatement(this.counter);
    }

    @Override
    public String toString() {
        return "Sleep(" + this.counter + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
