package controller;

import exceptions.ProgramException;
import model.program_state.ProgramState;

import java.util.List;

public interface IController {
    List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates);

    void executeStepForAllPrograms(List<ProgramState> programStates) throws ProgramException;
    void executeProgram() throws ProgramException ;

    void setDisplayFlag(boolean status);
    void setProgramLogFile(String logFileName);
    void displayProgramState(ProgramState state);
}
