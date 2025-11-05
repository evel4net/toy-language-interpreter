package exceptions;

public class VariableNotDefinedException extends ProgramException {
    public VariableNotDefinedException(String variableName) {
        super("Variable " + variableName + " is not defined.");
    }
}
