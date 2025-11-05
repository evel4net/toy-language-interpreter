package controller;

import model.adt.stack.EmptyStackException;
import exceptions.ProgramException;
import model.program_state.ExecutionStack;
import model.program_state.ProgramState;
import model.statements.Statement;
import repository.IRepository;

public class Controller implements IController {
    private final IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    @Override
    public void addProgramState(ProgramState state) {
        this.repository.addProgramState(state);
    }

    @Override
    public ProgramState executeStep(ProgramState state) throws EmptyStackException {
        ExecutionStack executionStack = state.getExecutionStack();

        if (executionStack.isEmpty()) throw new EmptyStackException();

        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(state);
    }

    @Override
    public void executeProgram() {
        ProgramState currentProgramState = this.repository.getCurrentProgramState();
        if (currentProgramState == null) throw new ProgramException("There is no program to execute.");

        this.repository.logProgramState();
        this.displayProgramState(currentProgramState);

        ExecutionStack executionStack = currentProgramState.getExecutionStack();

        while (!executionStack.isEmpty()) {
            this.executeStep(currentProgramState);
            this.repository.logProgramState();
            this.displayProgramState(currentProgramState);
        }
    }

    @Override
    public void setDisplayFlag(boolean status) {
        this.displayFlag = status;
    }

    @Override
    public void setProgramLogFile(String logFileName) {
        if (!logFileName.isEmpty()) this.repository.setLogFile(logFileName);
    }

    @Override
    public void displayProgramState(ProgramState state) {
        if (this.displayFlag) System.out.println(state.toString());
    }
}
