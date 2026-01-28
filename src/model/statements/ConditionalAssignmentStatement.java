package model.statements;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class ConditionalAssignmentStatement implements Statement {
    private final String variable;
    private final Expression conditionExpression, trueExpression, falseExpression;

    public ConditionalAssignmentStatement(String variable, Expression conditionExpression, Expression trueExpression, Expression falseExpression) {
        this.variable = variable;
        this.conditionExpression = conditionExpression;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Statement newStatement = new IfStatement(
                this.conditionExpression,
                new AssignmentStatement(this.variable, this.trueExpression),
                new AssignmentStatement(this.variable, this.falseExpression));

        state.getExecutionStack().push(newStatement);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeConditionExpression = this.conditionExpression.typeCheck(typeEnvironment);

        if (typeConditionExpression.equals(new BoolType())) {
            Type typeTrueExpression = this.trueExpression.typeCheck(typeEnvironment);
            Type typeFalseExpression = this.falseExpression.typeCheck(typeEnvironment);
            Type typeVariable = typeEnvironment.get(this.variable);

            if (typeTrueExpression.equals(typeVariable) && typeFalseExpression.equals(typeVariable)) return typeEnvironment;
            else throw new InvalidTypeException("Conditional assignment expressions type mismatch with variable type.");
        } else throw new InvalidTypeException("Conditional assignment expression is not of boolean type.");
    }

    @Override
    public Statement deepCopy() {
        return new ConditionalAssignmentStatement(this.variable, this.conditionExpression.deepCopy(), this.trueExpression.deepCopy(), this.falseExpression.deepCopy());
    }

    @Override
    public String toString() {
        return this.variable + "=" + this.conditionExpression.toString() + "?" + this.trueExpression.toString() + ":" + this.falseExpression.toString();
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
