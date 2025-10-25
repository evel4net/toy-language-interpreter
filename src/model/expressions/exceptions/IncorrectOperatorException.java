package model.expressions.exceptions;

public class IncorrectOperatorException extends ExpressionException {
    public IncorrectOperatorException(String operator) {
        super("Operator " + operator + " is not valid.");
    }
}
