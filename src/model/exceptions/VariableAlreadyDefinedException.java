package model.exceptions;

public class VariableAlreadyDefinedException extends ProgramException {
    public VariableAlreadyDefinedException(String variableName) {
        super("Variable " + variableName + " is already defined.");
    }
}
