package controller;

import exceptions.ProgramException;
import model.program_state.ProgramState;

import java.util.List;

public interface IController {
    List<ProgramState> removeCompletedProgramStates();

    void executeStepForAllPrograms(List<ProgramState> programStates) throws ProgramException;
    void executePrograms() throws ProgramException;
    void finishProgramExecution();

    void setDisplayFlag(boolean status);
    void setProgramLogFile(String logFileName);
    void displayProgramState(ProgramState state);

    List<ProgramState> getProgramStates();
}
