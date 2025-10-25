package repository;

import model.adt.list.InvalidIndexException;
import model.program_state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<>();
    private int currentProgramIndex = 0;

    @Override
    public ProgramState getCurrentProgramState() {
        return this.programStates.get(this.currentProgramIndex);
    }

    @Override
    public void setCurrentProgramState(int index) throws InvalidIndexException {
        if (index < 0 || index >= this.programStates.size()) throw new InvalidIndexException(index);

        this.currentProgramIndex = index;
    }

    @Override
    public void addProgramState(ProgramState state) {
        this.programStates.add(state);
        this.currentProgramIndex = this.programStates.size() - 1;
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return List.copyOf(this.programStates);
    }


}
