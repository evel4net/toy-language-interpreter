package repository;

import model.adt.list.InvalidIndexException;
import exceptions.FileNotFoundException;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);

    void setLogFile(String logFileName);
    void logProgramState(ProgramState programState) throws FileNotFoundException;
}
