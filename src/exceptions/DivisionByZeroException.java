package exceptions;

public class DivisionByZeroException extends ProgramException {
    public DivisionByZeroException() {
        super("Division by zero is not valid.");
    }
}
