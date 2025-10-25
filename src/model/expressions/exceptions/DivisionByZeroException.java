package model.expressions.exceptions;

public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException() {
        super("Division by zero is not valid.");
    }
}
