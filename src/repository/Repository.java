package repository;

import model.adt.list.InvalidIndexException;
import exceptions.FileNotFoundException;
import model.program_state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<>();
    private int currentProgramIndex;
    private String logFileName;

//    public Repository(ProgramState originalState, String logFileName) {
//        this.addProgramState(originalState);
//        this.setLogFile(logFileName);
//    }

    public Repository() {
        this.currentProgramIndex = -1;
        this.setLogFile("logfile.txt");
    }

    @Override
    public ProgramState getCurrentProgramState() {
        try {
            return this.programStates.get(this.currentProgramIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
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

    @Override
    public void setLogFile(String logFileName) {
        if (!logFileName.isEmpty()) this.logFileName = "./" + logFileName;
    }

    @Override
    public void logProgramState() throws FileNotFoundException {
        ProgramState currentProgramState = this.getCurrentProgramState();

        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFileName, true)));

            logFile.println("ExecutionStack:");
            logFile.print(currentProgramState.getExecutionStack().toLogFileString());

            logFile.println("SymbolsTable:");
            logFile.print(currentProgramState.getSymbolsTable().toLogFileString());

            logFile.println("Output:");
            logFile.print(currentProgramState.getOutput().toLogFileString());

            logFile.println("FileTable:");
            logFile.print(currentProgramState.getFileTable().tologFileString());

            logFile.println("HeapTable:");
            logFile.println(currentProgramState.getHeapTable().tologFileString());

            logFile.flush();
            logFile.close();
        } catch (IOException e) {
            throw new FileNotFoundException(this.logFileName);
        }
    }
}
