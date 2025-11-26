package view.commands;

import controller.IController;
import exceptions.ProgramException;

public class RunExampleCommand extends Command {
    private final IController controller;

    public RunExampleCommand(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.executePrograms();
        } catch (ProgramException e) {
            System.out.println(e.getMessage());
        }
    }
}
