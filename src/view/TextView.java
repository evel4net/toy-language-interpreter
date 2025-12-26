package view;

import controller.IController;
import model.expressions.*;
import model.program_state.*;
import model.statements.*;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class TextView {
    public static void run(ExamplesLoader loader) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));

        int index = 1;
        for (IController exampleController : loader.getAllExampleControllers()) {
            menu.addCommand(new RunExampleCommand(Integer.toString(index), loader.getExampleString(index - 1), exampleController));

            index++;
        }

        menu.show();
    }
}
