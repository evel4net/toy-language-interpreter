package controller;

import model.adt.stack.EmptyStackException;
import model.program_state.ProgramState;

import java.util.List;

public interface IController {
    void addProgramState(ProgramState state);
    List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates);

    ProgramState executeStep(ProgramState state) throws EmptyStackException;
    void executeProgram();

    void setDisplayFlag(boolean status);
    void setProgramLogFile(String logFileName);
    void displayProgramState(ProgramState state);
}
