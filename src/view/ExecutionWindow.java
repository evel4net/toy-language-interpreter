package view;

import controller.IController;
import exceptions.ProgramException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.program_state.*;

import java.util.List;

public class ExecutionWindow {
    private final IController controller;
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
    private final TextField programStatesCount = new TextField("0");
    @FXML
    private final ListView<Integer> programStatesID = new ListView<>();

    @FXML
    private final TableView<Pair<String, String>> symbolsTableView = new TableView<>();
    @FXML
    private final TableColumn<Pair<String, String>, String> symbolsTableView_nameColumn = new TableColumn<>("Name");
    @FXML
    private final TableColumn<Pair<String, String>, String> symbolsTableView_valueColumn = new TableColumn<>("Value");

    @FXML
    private final Button runOneStepButton = new Button("Run one step");
    @FXML
    private final Button runButton = new Button("Run");

    @FXML
    private final ListView<String> executionStackListView = new ListView<>();

    @FXML
    private final TextArea consoleBox = new TextArea();

    public ExecutionWindow(IController controller) {
        this.controller = controller;
        this.controller.resetToOriginalProgram();

        this.currentProgramState = this.controller.getProgramStates().get(0);
    }

    public void start() {
        this.load();

        this.refresh();
    }

    public void load() {
        HBox root = new HBox(1);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // --- display program

        TextArea program = new TextArea(this.controller.getProgramStates().get(0).getOriginalProgram().toString()); // TODO make a prettier toString
        program.setEditable(false);
        program.setWrapText(true);
        program.setPrefWidth(300);

        // --- execution information

        GridPane grid = new GridPane();
        grid.setPrefWidth(700);
        grid.setHgap(10);
        grid.setVgap(10);
        GridPane.setFillWidth(grid, true);

        // --- shared resources

        Text sharedText = new Text("Shared Resources");
        sharedText.setStyle("-fx-font-weight: bold;");
        grid.add(sharedText, 0, 0);

        // HEAP TABLE
        Text heapTableText = new Text("Heap Table");

        this.heapTableView_addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        this.heapTableView_valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
        this.heapTableView.getColumns().addAll(this.heapTableView_addressColumn, this.heapTableView_valueColumn);
        this.heapTableView.setEditable(false);
        this.heapTableView.getSelectionModel().setCellSelectionEnabled(false);
        this.heapTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        grid.add(heapTableText, 0, 1);
        grid.add(this.heapTableView, 0, 2);

        // OUTPUT
        Text outputText = new Text("Output");

        this.outputListView.setEditable(false);

        grid.add(outputText, 1, 1);
        grid.add(this.outputListView, 1, 2);

        // FILE TABLE
        Text fileTableText = new Text("Files Table");

        this.fileTableListView.setEditable(false);

        grid.add(fileTableText, 2, 1);
        grid.add(this.fileTableListView, 2, 2);

        // PROGRAM STATES COUNT
        Text programStatesCountText = new Text("Program States Counter");

        this.programStatesCount.setEditable(false);

        grid.add(programStatesCountText, 0, 3);
        grid.add(this.programStatesCount, 1, 3);

        // --- current state resources

        Text currentStateText = new Text("Current State Data");
        currentStateText.setStyle("-fx-font-weight: bold;");
        grid.add(currentStateText, 0, 4);

        // PROGRAM STATES IDS
        Text programStatesIDText = new Text("Program States Identifiers");

        this.programStatesID.setEditable(false);

        this.programStatesID.setOnMouseClicked(mouseEvent -> {
            if (this.programStatesID.getSelectionModel().getSelectedIndex() < 0) return;

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

        // SYMBOLS TABLE
        Text symbolsTableText = new Text("Symbols Table");

        this.symbolsTableView_nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        this.symbolsTableView_valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        this.symbolsTableView.getColumns().addAll(this.symbolsTableView_nameColumn, this.symbolsTableView_valueColumn);
        this.symbolsTableView.setEditable(false);
        this.symbolsTableView.getSelectionModel().setCellSelectionEnabled(false);
        this.symbolsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        grid.add(symbolsTableText, 1, 5);
        grid.add(this.symbolsTableView, 1, 6);

        // EXECUTION STACK
        Text executionStackText = new Text("Execution Stack");

        this.executionStackListView.setEditable(false);

        grid.add(executionStackText, 2, 5);
        grid.add(this.executionStackListView, 2, 6);

        // --- console for messages/errors

        Text consoleBoxText = new Text("Console");
        consoleBoxText.setStyle("-fx-font-weight: bold;");

        this.consoleBox.setEditable(false);
        this.consoleBox.setWrapText(true);
        grid.add(consoleBoxText, 0, 8);
        grid.add(this.consoleBox, 0, 9, 3, 1);

        // --- buttons

        VBox buttonsBox = new VBox(1);
        buttonsBox.setSpacing(10);

        grid.add(this.runOneStepButton, 0, 7);
        grid.add(this.runButton, 1, 7);

        this.runOneStepButton.setOnAction(actionEvent -> {
            this.runOneStepEvent();

            this.refresh();
        });

        this.runButton.setOnAction(actionEvent -> {
            this.runEvent();

            this.refresh();
        });

        buttonsBox.getChildren().addAll(this.runOneStepButton, this.runButton);
        HBox.setHgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.setPrefWidth(160);

        // --- set scene

        HBox.setHgrow(program, Priority.SOMETIMES);
        HBox.setHgrow(grid, Priority.SOMETIMES);
        root.getChildren().addAll(program, grid, buttonsBox);

        Scene scene = new Scene(root, 1000, 700);
        Stage stage = new Stage();
        stage.sizeToScene();
        stage.setTitle("Toy Language Interpreter");
        stage.setScene(scene);
        stage.show();
    }

    private void runOneStepEvent() {
        List<ProgramState> programStates = this.controller.removeCompletedProgramStates();

        if (!programStates.isEmpty()) {
            try {
                this.controller.executeStepForAllPrograms(programStates);
            } catch (ProgramException e) {
                this.displayConsoleMessage(e.getMessage(), true);

                this.controller.finishProgramExecution();

                this.disableButtons();
            }
        } else {
            this.controller.finishProgramExecution();

            this.disableButtons();

            this.displayConsoleMessage("Program execution finished.", false);
        }
    }

    private void runEvent() {
        try {
            this.controller.executePrograms();

            this.displayConsoleMessage("Program execution finished.", false);
        } catch (ProgramException e) {
            this.displayConsoleMessage(e.getMessage(), true);
        } finally {
            this.disableButtons();
        }
    }

    private void disableButtons() {
        this.runOneStepButton.setDisable(true);
        this.runButton.setDisable(true);
    }

    private void displayConsoleMessage(String message, boolean isError) {
        this.consoleBox.setText(message);

        String color = "black";
        if (isError) color = "red";

        this.consoleBox.setStyle("-fx-text-fill: " + color + ";");
    }

    public void refresh() {
        this.loadHeapTable();
        this.loadOutput();
        this.loadFileTable();

        this.loadProgramStates();

        this.loadSymbolsTable();
        this.loadExecutionStack();
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
        programStates.forEach(state -> {
            this.programStatesID.getItems().add(state.getId());

            if (state.getId() == this.currentProgramState.getId()) {
                this.programStatesID.getSelectionModel().selectLast();
            }
        });
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
