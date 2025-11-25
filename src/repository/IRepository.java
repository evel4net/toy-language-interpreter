package repository;

import model.adt.list.InvalidIndexException;
import exceptions.FileNotFoundException;
import model.program_state.ProgramState;

import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void setCurrentProgramState(int index) throws InvalidIndexException;

    void addProgramState(ProgramState state);
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);

    void setLogFile(String logFileName);
    void logProgramState() throws FileNotFoundException;
}
