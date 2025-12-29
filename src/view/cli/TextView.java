package view.cli;

import controller.IController;
import view.ExamplesLoader;
import view.cli.commands.ExitCommand;
import view.cli.commands.RunExampleCommand;

public class TextView {
    public static void run(ExamplesLoader loader) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));

        int index = 1;
        for (IController exampleController : loader.getAllControllers()) {
            menu.addCommand(new RunExampleCommand(Integer.toString(index), loader.getString(index - 1), exampleController));

            index++;
        }

        menu.show();
    }
}
