package controller;

import model.adt.stack.EmptyStackException;
import model.program_state.ProgramState;

public interface IController {
    ProgramState executeStep(ProgramState state) throws EmptyStackException;
    void executeProgram();

    void setDisplayFlag(boolean status);
    void displayProgramState(ProgramState state);
}
