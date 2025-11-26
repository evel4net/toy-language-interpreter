package repository;

import exceptions.FileNotFoundException;
import model.program_state.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);

    void setLogFile(String logFileName);
    void logProgramState(ProgramState programState) throws FileNotFoundException;
}
