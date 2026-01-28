package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.expressions.LogicExpression;
import model.expressions.RelationalExpression;
import model.program_state.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class RepeatUntilStatement implements Statement {
    private final Statement statement;
    private final Expression expression;

    public RepeatUntilStatement(Statement statement, Expression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Expression notExpression;

        if (this.expression instanceof LogicExpression) {
            notExpression = ((LogicExpression) this.expression).not();
        }
        else if (this.expression instanceof RelationalExpression) {
            notExpression = ((RelationalExpression) this.expression).not();
        }
        else throw new InvalidTypeException("Repeat until expression is not of boolean type.");

        Statement newStatement = new CompoundStatement(
                this.statement,
                new WhileStatement(notExpression, this.statement.deepCopy())
        );

        state.getExecutionStack().push(newStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression = this.expression.typeCheck(typeEnvironment);

        if (typeExpression.equals(new BoolType())) {
            this.statement.typeCheck(typeEnvironment.deepClone());

            return typeEnvironment;
        }
        else throw new InvalidTypeException("Repeat until expression is not of boolean type.");
    }

    @Override
    public Statement deepCopy() {
        return new RepeatUntilStatement(this.statement.deepCopy(), this.expression.deepCopy());
    }

    public String toString() {
        return "Repeat (" + this.statement.toString() + ") Until (" + this.expression.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return "Repeat (\n\t" + this.statement.toPrettyString() + "\n) Until (" + this.expression.toString() + ");";
    }
}
