import view.ExamplesLoader;
import view.gui.GUIView;

public class Main {
    public static void main(String[] args) {
        ExamplesLoader loader = new ExamplesLoader();

        GUIView.setExamplesLoader(loader);
        GUIView.run(args);
    }
}