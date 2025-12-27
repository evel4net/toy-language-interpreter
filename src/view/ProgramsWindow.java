package view;

import exceptions.ProgramException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProgramsWindow {
    private ExamplesLoader examplesLoader;

    public ProgramsWindow(ExamplesLoader loader) {
        this.examplesLoader = loader;
    }

    public void start(Stage stage) {
        HBox root = new HBox(4);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        ObservableList<String> programs = FXCollections.observableArrayList(this.examplesLoader.getAllStrings());
        ListView<String> programsList = new ListView<>(programs);
        programsList.getSelectionModel().select(0);
//        TextField programText = new TextField("My program ...");
//        programText.setEditable(false);

        Button runButton = new Button("Run");
        runButton.setOnAction(actionEvent -> {
            int index = programsList.getSelectionModel().getSelectedIndex();
            if (index < 0) throw new ProgramException("No program selected.");

//            this.setMainView(stage, this.examplesLoader.getController(index));
            ExecutionWindow programWindow = new ExecutionWindow(this.examplesLoader.getController(index));
            programWindow.start();
        });

        root.getChildren().addAll(programsList, runButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
