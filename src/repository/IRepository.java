package repository;

import model.program_state.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void addProgramState(ProgramState state);
}
