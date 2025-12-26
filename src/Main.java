import view.ExamplesLoader;
import view.GUI;
import view.TextView;

public class Main {
    public static void main(String[] args) {
        ExamplesLoader loader = new ExamplesLoader();

        GUI.setExamplesLoader(loader);
        GUI.run(args);
    }
}