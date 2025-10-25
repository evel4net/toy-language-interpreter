package view;

import controller.IController;
import model.adt.list.InvalidIndexException;
import model.program_state.ProgramState;
import repository.IRepository;

import java.util.Scanner;

public class TextView implements IView {
    private final IRepository repository;
    private final IController controller;
    private final Scanner scanner = new Scanner(System.in);

    public TextView(IRepository repository, IController controller) {
        this.repository = repository;
        this.controller = controller;
    }

    private void printMenu() {
        System.out.println();
        System.out.println("--------------------");
        System.out.println("1. Input program");
        System.out.println("0. Exit");
    }

    @Override
    public void start() {
        String userOption = "";

        while (true) {
            this.printMenu();

            System.out.print("> ");
            userOption = this.scanner.next();

            switch (userOption) {
                case "0":
                    return;
                case "1":
                    this.inputProgram();
                    break;
                default:
                    break;
            }
        }
    }

    private void inputProgram() {
        System.out.println("--- Input Program");
        System.out.println("1. New program");
        System.out.println("2. Choose available program");

        System.out.print("> ");
        String userOption = this.scanner.next();

        switch (userOption) {
            case "1":
                this.addNewProgram();
                break;
            case "2":
                this.chooseProgram();
                break;
            default:
                break;
        }
    }

    private void addNewProgram() {
        System.out.println("--- New program");

        //TODO
    }

    private void chooseProgram() {
        System.out.println("--- Choose available program");

        int index = 0;
        for (ProgramState state : this.repository.getProgramStates()) {
            System.out.println(index + ". " + state.getOriginalProgram().toString());
            index++;
        }

        System.out.print("> ");
        String userOption = this.scanner.next();
        int exampleIndex = Integer.parseInt(userOption);

        try {
            this.repository.setCurrentProgramState(exampleIndex);
            this.controller.executeProgram();
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }
}
