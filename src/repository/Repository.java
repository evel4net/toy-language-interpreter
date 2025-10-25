package repository;

import model.program_state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<>();

    @Override
    public ProgramState getCurrentProgramState() {
        return this.programStates.get(0);
    }

    @Override
    public void addProgramState(ProgramState state) {
        this.programStates.add(state);
    }
}
