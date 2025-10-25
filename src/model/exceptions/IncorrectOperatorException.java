package model.exceptions;

public class IncorrectOperatorException extends ProgramException {
    public IncorrectOperatorException(String operator) {
        super("Operator " + operator + " is not valid.");
    }
}
