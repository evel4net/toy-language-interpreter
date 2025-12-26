package view;

import controller.IController;
import exceptions.ProgramException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repository.IRepository;

public class GUI extends Application {
    private static ExamplesLoader examplesLoader;

    public static void run(String[] args) {
        launch(args);
    }

    public static void setExamplesLoader(ExamplesLoader loader) {
        GUI.examplesLoader = loader;
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Toy Language Interpreter");

            this.setProgramView(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgramView(Stage stage) {
        HBox root = new HBox(4);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        ObservableList<String> programs = FXCollections.observableArrayList(GUI.examplesLoader.getAllExampleStrings());
        ListView<String> programsList = new ListView<>(programs);

//        TextField programText = new TextField("My program ...");
//        programText.setEditable(false);

        Button runButton = new Button("Run");
        runButton.setOnAction(actionEvent -> {
            int index = programsList.getSelectionModel().getSelectedIndex();
            if (index < 0) throw new ProgramException("No program selected.");

            this.setMainView(stage, this.examplesLoader.getExampleController(index));
        });

        root.getChildren().addAll(programsList, runButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void setMainView(Stage stage, IController programController) {
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

        TableView<IController> heapTable = new TableView<>();

        TableColumn<IController, String> addressColumn = new TableColumn<>("Address");
//        addressColumn.setCellValueFactory(data -> data.getValue());
        TableColumn<IController, String> valueColumn = new TableColumn<>("Value");
        heapTable.getColumns().addAll(addressColumn, valueColumn);

        grid.add(heapTableText, 0, 1);
        grid.add(heapTable, 0, 2);

        // -- OUTPUT
        Text outputText = new Text("Output");

        ListView<String> output = new ListView<>();

        grid.add(outputText, 1, 1);
        grid.add(output, 1, 2);

        // -- FILE TABLE
        Text fileTableText = new Text("Files Table");

        ListView<String> fileTable = new ListView<>();

        grid.add(fileTableText, 2, 1);
        grid.add(fileTable, 2, 2);

        // -- PROGRAM STATES COUNT

        Text programStatesCountText = new Text("Program States Counter");

        TextField programStatesCount = new TextField("5");

        grid.add(programStatesCountText, 0, 3);
        grid.add(programStatesCount, 1, 3);

        // -- current state

        Text currentStateText = new Text("Current State Data");
        currentStateText.setStyle("-fx-font-weight: bold;");
        grid.add(currentStateText, 0, 4);

        // -- PROGRAM STATES IDS

        Text programStatesIDText = new Text("Program States Identifiers");

        ListView<Integer> programStatesID = new ListView<>();
        programStatesID.getSelectionModel().select(0);

        grid.add(programStatesIDText, 0, 5);
        grid.add(programStatesID, 0, 6);

        // -- SYM TABLE

        Text symbolsTableText = new Text("Symbols Table");

        TableView<IController> symbolsTable = new TableView<>();

        TableColumn<IController, String> nameColumn = new TableColumn<>("Name");
        TableColumn<IController, String> symbolsValueColumn = new TableColumn<>("Value");

        symbolsTable.getColumns().addAll(nameColumn, symbolsValueColumn);

        grid.add(symbolsTableText, 1, 5);
        grid.add(symbolsTable, 1, 6);

        // -- EXE STACK

        Text executionStackText = new Text("Execution Stack");

        ListView<String> executionStack = new ListView<>();

        grid.add(executionStackText, 2, 5);
        grid.add(executionStack, 2, 6);

        // -- RUN BUTTON

        Button runOneStepButton = new Button("Run one step");
        grid.add(runOneStepButton, 0, 7);

        // scene

        Scene scene = new Scene(grid, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
