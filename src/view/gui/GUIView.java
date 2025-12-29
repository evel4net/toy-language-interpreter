package view.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ExamplesLoader;

public class GUIView extends Application {
    private static ExamplesLoader examplesLoader;

    public static void run(String[] args) {
        launch(args);
    }

    public static void setExamplesLoader(ExamplesLoader loader) {
        GUIView.examplesLoader = loader;
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Toy Language Interpreter");

            ProgramsWindow window = new ProgramsWindow(GUIView.examplesLoader);
            window.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
