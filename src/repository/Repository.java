package repository;

import exceptions.FileNotFoundException;
import model.program_state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates = new ArrayList<>();
    private String logFileName;

    public Repository(ProgramState originalState, String logFileName) {
        this.programStates.add(originalState);
        this.setLogFile(logFileName);
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return List.copyOf(this.programStates);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void setLogFile(String logFileName) {
        if (!logFileName.isEmpty()) this.logFileName = "./" + logFileName;
    }

    @Override
    public void logProgramState(ProgramState programState) throws FileNotFoundException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFileName, true)));

            logFile.println("----- Program ID: " + programState.getId());

            logFile.println("ExecutionStack:");
            logFile.print(programState.getExecutionStack().toLogFileString());

            logFile.println("SymbolsTable:");
            logFile.print(programState.getSymbolsTable().toLogFileString());

            logFile.println("Output:");
            logFile.print(programState.getOutput().toLogFileString());

            logFile.println("FileTable:");
            logFile.print(programState.getFileTable().tologFileString());

            logFile.println("HeapTable:");
            logFile.println(programState.getHeapTable().tologFileString());

            logFile.flush();
            logFile.close();
        } catch (IOException e) {
            throw new FileNotFoundException(this.logFileName);
        }
    }
}
