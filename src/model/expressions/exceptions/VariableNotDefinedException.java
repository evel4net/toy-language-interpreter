package model.expressions.exceptions;

public class VariableNotDefinedException extends ExpressionException {
    public VariableNotDefinedException(String variableName) {
        super("Variable " + variableName + " is not defined.");
    }
}
