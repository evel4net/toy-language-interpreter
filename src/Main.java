import controller.Controller;
import controller.IController;
import model.expressions.ArithmeticExpression;
import model.expressions.Expression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.program_state.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.ConsoleView;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Main {


    public static void main(String[] args) {
        // Example 1 : int v; v = 2; Print(v)
        Statement example1 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        ProgramState state1 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example1);
        IRepository repository1 = new Repository();
        IController controller1 = new Controller(repository1, true);
        controller1.addProgramState(state1);
        controller1.setProgramLogFile("logFile1.txt");

        // Example 2 : int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b)
        Expression assignment_a = new ArithmeticExpression(
                new ValueExpression(new IntValue(2)),
                new ArithmeticExpression(
                        new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5)),
                        '*'
                ),
                '+'
        );
        Expression assignment_b = new ArithmeticExpression(
                new VariableExpression("a"),
                new ValueExpression(new IntValue(1)),
                '+'
        );
        Statement example2 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "b"),
                        new CompoundStatement(
                                new AssignmentStatement("a", assignment_a),
                                new CompoundStatement(
                                        new AssignmentStatement("b", assignment_b),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

        ProgramState state2 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example2);
        IRepository repository2 = new Repository();
        IController controller2 = new Controller(repository2, true);
        controller2.addProgramState(state2);
        controller2.setProgramLogFile("logFile2.txt");

        // Example 3 : bool a; int v; a = true; (If a Then v = 2 Else v = 3); Print(v)
        Statement example3 = new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        ProgramState state3 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example3);
        IRepository repository3 = new Repository();
        IController controller3 = new Controller(repository3, true);
        controller3.addProgramState(state3);
        controller3.setProgramLogFile("logFile3.txt");

        // Example 4:
        // string varf;
        //varf="test.in";
        //openRFile(varf);
        //int varc;
        //readFile(varf,varc);print(varc);
        //readFile(varf,varc);print(varc)
        //closeRFile(varf)

        Statement example4 = new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState state4 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example4);
        IRepository repository4 = new Repository();
        IController controller4 = new Controller(repository4, true);
        controller4.addProgramState(state4);
        controller4.setProgramLogFile("logFile4.txt");

        // ---

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));

        menu.show();
    }

    public void oldMain() {
        // Example 1 : int v; v = 2; Print(v)
        Statement example1 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v"))
                )
        );

        // Example 2 : int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b)
        Expression assignment_a = new ArithmeticExpression(
                new ValueExpression(new IntValue(2)),
                new ArithmeticExpression(
                        new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5)),
                        '*'
                ),
                '+'
        );
        Expression assignment_b = new ArithmeticExpression(
                new VariableExpression("a"),
                new ValueExpression(new IntValue(1)),
                '+'
        );
        Statement example2 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "b"),
                        new CompoundStatement(
                                new AssignmentStatement("a", assignment_a),
                                new CompoundStatement(
                                        new AssignmentStatement("b", assignment_b),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

        // Example 3 : bool a; int v; a = true; (If a Then v = 2 Else v = 3); Print(v)
        Statement example3 = new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        // Example 4:
        // string varf;
        //varf="test.in";
        //openRFile(varf);
        //int varc;
        //readFile(varf,varc);print(varc);
        //readFile(varf,varc);print(varc)
        //closeRFile(varf)

        Statement example4 = new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // -----

        ProgramState state1 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example1);
        ProgramState state2 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example2);
        ProgramState state3 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example3);
        ProgramState state4 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example4);

        IRepository repository = new Repository();

        IController controller = new Controller(repository, true);
        controller.addProgramState(state1);
        controller.addProgramState(state2);
        controller.addProgramState(state3);
        controller.addProgramState(state4);

        ConsoleView view = new ConsoleView(repository, controller);
        view.start();
    }
}