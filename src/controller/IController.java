package controller;

import model.adt.stack.EmptyStackException;
import model.program_state.ProgramState;

public interface IController {
    void addProgramState(ProgramState state);

    ProgramState executeStep(ProgramState state) throws EmptyStackException;
    void executeProgram();

    void setDisplayFlag(boolean status);
    void setProgramLogFile(String logFileName);
    void displayProgramState(ProgramState state);
}
