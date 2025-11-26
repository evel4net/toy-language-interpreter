package controller;

import exceptions.ProgramException;
import model.program_state.HeapTable;
import model.program_state.ProgramState;
import model.values.Value;
import repository.IRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepository repository;
    private boolean displayFlag;
    private ExecutorService executor;
    private final GarbageCollector garbageCollector = new GarbageCollector();

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    @Override
    public List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void executeStepForAllPrograms(List<ProgramState> programStates) throws ProgramException {
        programStates.forEach(this.repository::logProgramState);

        // get list of callables
        List<Callable<ProgramState>> callablesList = programStates.stream()
                .map(program -> (Callable<ProgramState>)(program::executeStep))
                .collect(Collectors.toList());

        // execute callables --> get list of threads
        List<ProgramState> threadsProgramStates = null;
        try {
            threadsProgramStates = this.executor.invokeAll(callablesList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new ProgramException("Concurrent thread execution error: " + e.getMessage());
                        }
                    })
                    .filter(program -> program != null) // result of fork statement
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new ProgramException("Concurrent thread execution error.");
        }

        programStates.addAll(threadsProgramStates);

        programStates.forEach(this.repository::logProgramState);
        programStates.forEach(this::displayProgramState);

        this.repository.setProgramStates(programStates);
    }

    @Override
    public void executePrograms() throws ProgramException {
        this.executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStates = this.removeCompletedProgramStates(this.repository.getProgramStates());

        while (!programStates.isEmpty()) {
            this.cleanHeapContent(programStates);

            this.executeStepForAllPrograms(programStates);

            programStates = this.removeCompletedProgramStates(this.repository.getProgramStates());
        }

        this.executor.shutdownNow();

        this.repository.setProgramStates(programStates);
    }

    public void cleanHeapContent(List<ProgramState> programStates) {
        HeapTable heap = programStates.getFirst().getHeapTable();

        Map<Integer, Value> garbageFreeHeapContent = this.garbageCollector.collect(
                this.garbageCollector.getAccessibleAddresses(programStates.stream().map(ProgramState::getSymbolsTable).collect(Collectors.toList()), heap),
                heap.getContent()
        );

        heap.setContent(garbageFreeHeapContent);
    }

    @Override
    public void setDisplayFlag(boolean status) {
        this.displayFlag = status;
    }

    @Override
    public void setProgramLogFile(String logFileName) {
        if (!logFileName.isEmpty()) this.repository.setLogFile(logFileName);
    }

    @Override
    public void displayProgramState(ProgramState state) {
        if (this.displayFlag) System.out.println(state.toString());
    }
}
