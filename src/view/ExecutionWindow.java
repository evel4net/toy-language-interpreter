package view;

import controller.IController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.program_state.*;

import java.util.List;

public class ExecutionWindow {
    private final IController controller;
    private boolean isExecutionFinished = false;
    private ProgramState currentProgramState;

    @FXML
    private final TableView<Pair<Integer, String>> heapTableView = new TableView<>();
    @FXML
    private final TableColumn<Pair<Integer, String>, Integer> heapTableView_addressColumn = new TableColumn<>("Address");
    @FXML
    private final TableColumn<Pair<Integer, String>, String> heapTableView_valueColumn = new TableColumn<>("Value");

    @FXML
    private final ListView<String> outputListView = new ListView<>();
    @FXML
    private final ListView<String> fileTableListView = new ListView<>();

    @FXML
    private final Text programStatesCount = new Text("0");
    @FXML
    private final ListView<Integer> programStatesID = new ListView<>();

    @FXML
    private final TableView<Pair<String, String>> symbolsTableView = new TableView<>();
    @FXML
    private final TableColumn<Pair<String, String>, String> symbolsTableView_nameColumn = new TableColumn<>("Name");
    @FXML
    private final TableColumn<Pair<String, String>, String> symbolsTableView_valueColumn = new TableColumn<>("Value");

    @FXML
    private final ListView<String> executionStackListView = new ListView<>();


    public ExecutionWindow(IController controller) {
        this.controller = controller;

        this.currentProgramState = this.controller.getProgramStates().get(0);
    }

    public void start() {
        this.load();

        this.refresh();
    }

    public void load() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // -- shared

        Text sharedText = new Text("Shared Resources"); // make bold
        sharedText.setStyle("-fx-font-weight: bold;");
        grid.add(sharedText, 0, 0);

        // -- HEAP TABLE
        Text heapTableText = new Text("Heap Table");

        this.heapTableView.getColumns().addAll(this.heapTableView_addressColumn, this.heapTableView_valueColumn);
        this.heapTableView.setEditable(false);
        this.heapTableView.getSelectionModel().setCellSelectionEnabled(false);

        grid.add(heapTableText, 0, 1);
        grid.add(this.heapTableView, 0, 2);

        // -- OUTPUT
        Text outputText = new Text("Output");

        this.outputListView.setEditable(false);
        // TODO list view set select disabled

        grid.add(outputText, 1, 1);
        grid.add(this.outputListView, 1, 2);

        // -- FILE TABLE
        Text fileTableText = new Text("Files Table");

        this.fileTableListView.setEditable(false);
        // TODO select disabled

        grid.add(fileTableText, 2, 1);
        grid.add(this.fileTableListView, 2, 2);

        // -- PROGRAM STATES COUNT

        Text programStatesCountText = new Text("Program States Counter");

        grid.add(programStatesCountText, 0, 3);
        grid.add(this.programStatesCount, 1, 3);

        // -- current state

        Text currentStateText = new Text("Current State Data");
        currentStateText.setStyle("-fx-font-weight: bold;");
        grid.add(currentStateText, 0, 4);

        // -- PROGRAM STATES IDS

        Text programStatesIDText = new Text("Program States Identifiers");

        this.programStatesID.setEditable(false);

        this.programStatesID.setOnMouseClicked(mouseEvent -> {
            int stateID = this.programStatesID.getSelectionModel().getSelectedItem();

            if (stateID != this.currentProgramState.getId()) {
                this.currentProgramState = this.controller.getProgramStates().stream()
                        .filter(state -> state.getId() == stateID)
                        .findFirst()
                        .orElse(null);

                this.refresh();
            }
        });

        grid.add(programStatesIDText, 0, 5);
        grid.add(this.programStatesID, 0, 6);

        // -- SYM TABLE

        Text symbolsTableText = new Text("Symbols Table");

        this.symbolsTableView.getColumns().addAll(this.symbolsTableView_nameColumn, this.symbolsTableView_valueColumn);
        this.symbolsTableView.setEditable(false);
        this.symbolsTableView.getSelectionModel().setCellSelectionEnabled(false);

        grid.add(symbolsTableText, 1, 5);
        grid.add(this.symbolsTableView, 1, 6);

        // -- EXE STACK

        Text executionStackText = new Text("Execution Stack");

        this.executionStackListView.setEditable(false);
        // TODO  set select disable

        grid.add(executionStackText, 2, 5);
        grid.add(this.executionStackListView, 2, 6);

        // -- RUN BUTTON

        Button runOneStepButton = new Button("Run one step");
        grid.add(runOneStepButton, 0, 7);

        runOneStepButton.setOnAction(actionEvent -> {
            this.runOneStepEvent();

            this.refresh();
        });

        // scene

        Scene scene = new Scene(grid, 500, 500);
        Stage stage = new Stage();
        stage.setTitle("Execution");
        stage.setScene(scene);
        stage.show();
    }

    private void runOneStepEvent() {
        if (this.isExecutionFinished) return;

        List<ProgramState> programStates = this.controller.removeCompletedProgramStates();

        if (!programStates.isEmpty()) {
            this.controller.executeStepForAllPrograms(programStates);
        } else {
            this.controller.finishProgramExecution();

            this.isExecutionFinished = true;
        }
    }

    public void refresh() {
        this.loadHeapTable();
        this.loadOutput();
        this.loadFileTable();

        this.loadProgramStates();

        this.loadSymbolsTable();
        this.loadExecutionStack();

        // TODO table views are not working
    }

    private void loadHeapTable() {
        HeapTable heapTable = this.currentProgramState.getHeapTable();

        this.heapTableView.getItems().clear();
        heapTable.getContent().forEach((address, value) -> this.heapTableView.getItems().add(new Pair<>(address, value.toString())));
    }

    private void loadOutput() {
        Output output = this.currentProgramState.getOutput();

        this.outputListView.getItems().clear();
        output.getContent().forEach(value -> this.outputListView.getItems().add(value.toString()));
    }

    private void loadFileTable() {
        FileTable fileTable = this.currentProgramState.getFileTable();

        this.fileTableListView.getItems().clear();
        fileTable.getContent().forEach(file -> this.fileTableListView.getItems().add(file.toString()));
    }

    private void loadProgramStates() {
        List<ProgramState> programStates = this.controller.removeCompletedProgramStates();

        this.programStatesCount.setText(Integer.toString(programStates.size()));

        this.programStatesID.getItems().clear();
        programStates.forEach(state -> this.programStatesID.getItems().add(state.getId()));
    }

    private void loadSymbolsTable() {
        SymbolsTable symbolsTable = this.currentProgramState.getSymbolsTable();

        this.symbolsTableView.getItems().clear();

        symbolsTable.getContent().forEach((name, value) -> this.symbolsTableView.getItems().add(new Pair<>(name, value.toString())));
    }

    private void loadExecutionStack() {
        ExecutionStack executionStack = this.currentProgramState.getExecutionStack();

        this.executionStackListView.getItems().clear();
        executionStack.getContent().forEach(statement -> this.executionStackListView.getItems().add(statement.toString()));
    }
}
