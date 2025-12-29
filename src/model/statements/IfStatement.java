package model.statements;

import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import exceptions.ProgramException;
import exceptions.InvalidTypeException;
import model.program_state.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement thenStatement, elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value expressionValue = this.expression.evaluate(state.getSymbolsTable(), state.getHeapTable());

        if (!(expressionValue instanceof BoolValue expressionValue_Bool)) throw new InvalidTypeException("If statement condition is not a boolean.");

        Statement finalStatement = expressionValue_Bool.getValue() ? this.thenStatement : this.elseStatement;
        state.getExecutionStack().push(finalStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (typeExpression.equals(new BoolType())) {
            this.thenStatement.typeCheck(typeEnvironment.deepClone());
            this.elseStatement.typeCheck(typeEnvironment.deepClone());

            return typeEnvironment;
        } else throw new InvalidTypeException("If statement condition is not a boolean.");
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "If (" + this.expression.toString() + ") then (" + this.thenStatement.toString() +
                ") else (" + this.elseStatement.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        String thenStatementString = Arrays.stream(this.thenStatement.toPrettyString().split("\n"))
                .map(str ->  { return "\t" + str; })
                .collect(Collectors.joining("\n"));

        String elseStatementString = Arrays.stream(this.elseStatement.toPrettyString().split("\n"))
                .map(str -> { return "\t" + str; })
                .collect(Collectors.joining("\n"));

        return "If (" + this.expression.toString() + ") then (\n" + thenStatementString +
                "\n) else (\n" + elseStatementString + "\n);";
    }
}
