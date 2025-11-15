import controller.Controller;
import controller.IController;
import model.expressions.*;
import model.program_state.*;
import model.statements.*;
import model.statements.file_operations.CloseReadFileStatement;
import model.statements.file_operations.OpenReadFileStatement;
import model.statements.file_operations.ReadFileStatement;
import model.statements.heap_operations.AllocateHeapStatement;
import model.expressions.ReadHeapExpression;
import model.statements.heap_operations.WriteHeapStatement;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Main {

    public static void main(String[] args) {
        // Example 1 : int v; v = 2; Print(v); Print(v <= 1)
        Statement example1 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(
                            new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new RelationalExpression(
                                        new VariableExpression("v"),
                                        new ValueExpression(new IntValue(1)),
                                        "<="))
                                )
                )
        );
        ProgramState state1 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example1);
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

        ProgramState state2 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example2);
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

        ProgramState state3 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example3);
        IRepository repository3 = new Repository();
        IController controller3 = new Controller(repository3, true);
        controller3.addProgramState(state3);
        controller3.setProgramLogFile("logFile3.txt");

        // Example 4:
        // string varf;
        //  varf="test.in";
        //  OpenRFile(varf);
        //  int varc;
        //  ReadFile(varf,varc); Print(varc);
        //  ReadFile(varf,varc); Print(varc)
        //  CloseRFile(varf)

        Statement example4 = new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState state4 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example4);
        IRepository repository4 = new Repository();
        IController controller4 = new Controller(repository4, true);
        controller4.addProgramState(state4);
        controller4.setProgramLogFile("logFile4.txt");

        // Example 5: Ref int v; new(v, 20); print(ReadHeap(v)); WriteHeap(v, 30); Print(ReadHeap(v) + 5);
        // -- heap operations example

        Statement example5 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        '+'))
                                )
                        )
                )
        );

        ProgramState state5 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example5);
        IRepository repository5 = new Repository();
        IController controller5 = new Controller(repository5, true);
        controller5.addProgramState(state5);
        controller5.setProgramLogFile("logFile5.txt");

        // Example 6: int v; v = 4; (While (v > 0) (Print(v); v = v - 1); Print(v)
        // -- while statement example

        Statement example6 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-'))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        ProgramState state6 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example6);
        IRepository repository6 = new Repository();
        IController controller6 = new Controller(repository6, true);
        controller6.addProgramState(state6);
        controller6.setProgramLogFile("logFile6.txt");

        // Example 7: Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(ReadHeap(ReadHeap(a)))
        // -- garbage collector example

        Statement example7 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new AllocateHeapStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

        ProgramState state7 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example7);
        IRepository repository7 = new Repository();
        IController controller7 = new Controller(repository7, true);
        controller7.addProgramState(state7);
        controller7.setProgramLogFile("logFile7.txt");

        // ---

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", example5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", example7.toString(), controller7));

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
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // -----

//        ProgramState state1 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example1);
//        ProgramState state2 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example2);
//        ProgramState state3 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example3);
//        ProgramState state4 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), example4);
//
//        IRepository repository = new Repository();
//
//        IController controller = new Controller(repository, true);
//        controller.addProgramState(state1);
//        controller.addProgramState(state2);
//        controller.addProgramState(state3);
//        controller.addProgramState(state4);
//
//        ConsoleView view = new ConsoleView(repository, controller);
//        view.start();
    }
}