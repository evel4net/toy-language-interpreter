package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
        stage.setTitle("Toy Language Interpreter");

        setProgramView(stage);
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

        root.getChildren().addAll(programsList, runButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
