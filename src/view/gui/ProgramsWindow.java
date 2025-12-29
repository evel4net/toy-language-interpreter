package view.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.ExamplesLoader;

public class ProgramsWindow {
    private final ExamplesLoader examplesLoader;

    public ProgramsWindow(ExamplesLoader loader) {
        this.examplesLoader = loader;
    }

    public void start(Stage stage) {
        VBox root = new VBox(1);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Text title = new Text("Select program to execute");

        // --- display all hardcoded programs

        ObservableList<String> programs = FXCollections.observableArrayList(this.examplesLoader.getAllStrings());
        ListView<String> programsList = new ListView<>(programs);
        programsList.getSelectionModel().select(0);

        // --- button

        Button selectButton = new Button("OK");
        selectButton.setOnAction(actionEvent -> {
            this.onSelectButtonClick(programsList.getSelectionModel().getSelectedIndex());
        });

        // --- set scene

        root.getChildren().addAll(title, programsList, selectButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void onSelectButtonClick(int programIndex) {
        if (programIndex < 0) return;

        ExecutionWindow programWindow = new ExecutionWindow(this.examplesLoader.getController(programIndex));
        programWindow.start();
    }
}
