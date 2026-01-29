package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.program_state.ProgramState;
import model.types.Type;

public class SwitchStatement implements Statement{
    private final Expression switchExpression, case1Expression, case2Expression;
    private final Statement case1Statement, case2Statement, defaultStatement;

    public SwitchStatement(Expression switchExpression, Expression case1Expression,
                           Statement case1Statement, Expression case2Expression,
                           Statement case2Statement, Statement defaultStatement) {
        this.switchExpression = switchExpression;
        this.case1Expression = case1Expression;
        this.case2Expression = case2Expression;
        this.case1Statement = case1Statement;
        this.case2Statement = case2Statement;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Statement newStatement = new IfStatement(
                new RelationalExpression(this.switchExpression, this.case1Expression, "=="),
                this.case1Statement,
                new IfStatement(
                        new RelationalExpression(this.switchExpression, this.case2Expression, "=="),
                        this.case2Statement,
                        this.defaultStatement
                )
        );

        state.getExecutionStack().push(newStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeSwitchExpression = this.switchExpression.typeCheck(typeEnvironment);
        Type typeCase1Expression = this.case1Expression.typeCheck(typeEnvironment);
        Type typeCase2Expression = this.case2Expression.typeCheck(typeEnvironment);

        if (!(typeSwitchExpression.equals(typeCase1Expression) && typeSwitchExpression.equals(typeCase2Expression))) throw new InvalidTypeException("Switch case expressions' types mismatch.");

        this.case1Statement.typeCheck(typeEnvironment);
        this.case2Statement.typeCheck(typeEnvironment);
        this.defaultStatement.typeCheck(typeEnvironment);

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new SwitchStatement(this.switchExpression.deepCopy(), this.case1Expression.deepCopy(),
                this.case1Statement.deepCopy(), this.case2Expression.deepCopy(),
                this.case2Statement.deepCopy(), this.defaultStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "Switch(" + this.switchExpression.toString() + ") (case " + this.case1Expression.toString() + ": " +
                this.case1Statement.toString() + ") (case " + this.case2Expression.toString() + ": " +
                this.case2Statement.toString() + ") (default: " + this.defaultStatement.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return "Switch(" + this.switchExpression.toString() + ") (case " + this.case1Expression.toString() + ":\n\t" +
                this.case1Statement.toString() + "\n) (case " + this.case2Expression.toString() + ":\n\t" +
                this.case2Statement.toString() + "\n) (default:\n\t" + this.defaultStatement.toString() + "\n);";
    }
}
