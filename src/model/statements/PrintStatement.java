package model.statements;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import exceptions.ProgramException;
import model.program_state.ProgramState;
import model.types.Type;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        state.getOutput().add(this.expression.evaluate(state.getSymbolsTable(), state.getHeapTable()));

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        this.expression.typeCheck(typeEnvironment);

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "Print(" + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
