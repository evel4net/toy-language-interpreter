package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        ObservableList<String> programs = FXCollections.observableArrayList(this.examplesLoader.getAllStrings());
        ListView<String> programsList = new ListView<>(programs);
        programsList.getSelectionModel().select(0);

        Button selectButton = new Button("OK");
        selectButton.setOnAction(actionEvent -> {
            int index = programsList.getSelectionModel().getSelectedIndex();
            if (index < 0) return;

            ExecutionWindow programWindow = new ExecutionWindow(this.examplesLoader.getController(index));
            programWindow.start();
        });

        root.getChildren().addAll(title, programsList, selectButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
