package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.program_state.ProgramState;
import model.types.IntType;
import model.types.Type;

public class ForStatement implements Statement {
    private final String variable;
    private final Expression initializationExpression, conditionExpression, updateExpression;
    private final Statement bodyStatement;

    public ForStatement(String variable, Expression initializationExpression,
                        Expression conditionExpression, Expression updateExpression,
                        Statement bodyStatement) {
        this.variable = variable;
        this.initializationExpression = initializationExpression;
        this.conditionExpression = conditionExpression;
        this.updateExpression = updateExpression;
        this.bodyStatement = bodyStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
//        Statement newStatement = new CompoundStatement(
//                new VariableDeclarationStatement(new IntType(), this.variable),
//                new CompoundStatement(
//                        new AssignmentStatement(this.variable, this.initializationExpression),
//                        new WhileStatement(new RelationalExpression(new VariableExpression(this.variable), this.conditionExpression, "<"),
//                                new CompoundStatement(
//                                    this.bodyStatement,
//                                    new AssignmentStatement(this.variable, this.updateExpression))
//                        )
//                )
//        );

        Statement newStatement = new CompoundStatement(
                new AssignmentStatement(this.variable, this.initializationExpression),
                new WhileStatement(new RelationalExpression(new VariableExpression(this.variable), this.conditionExpression, "<"),
                        new CompoundStatement(
                                this.bodyStatement,
                                new AssignmentStatement(this.variable, this.updateExpression)
                        )
                )
        );

        state.getExecutionStack().push(newStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeInitializationExpression = this.initializationExpression.typeCheck(typeEnvironment);
        if (!typeInitializationExpression.equals(new IntType())) throw new InvalidTypeException("For statement initialization expression is not of int type.");

        Type typeConditionExpression = this.conditionExpression.typeCheck(typeEnvironment);
        if (!typeConditionExpression.equals(new IntType())) throw new InvalidTypeException("For statement condition expression is not of int type.");

        Type typeUpdateExpression = this.updateExpression.typeCheck(typeEnvironment);
        if (!typeUpdateExpression.equals(new IntType())) throw new InvalidTypeException("For statement update expression is not of int type.");

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new ForStatement(this.variable, this.initializationExpression.deepCopy(),
                this.conditionExpression.deepCopy(), this.updateExpression.deepCopy(),
                this.bodyStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "For(" + this.variable + "=" + this.initializationExpression.toString() +
                "; " + this.variable + "<" + this.conditionExpression.toString() + "; " +
                this.variable + "=" + this.updateExpression.toString() + ") " + this.bodyStatement.toString();
    }

    @Override
    public String toPrettyString() {
        return "For(" + this.variable + "=" + this.initializationExpression.toString() +
                "; " + this.variable + "<" + this.conditionExpression.toString() + "; " +
                this.variable + "=" + this.updateExpression.toString() + ") (\n\t" + this.bodyStatement.toString() + "\n)";
    }
}
