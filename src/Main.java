import controller.Controller;
import controller.IController;
import model.adt.dictionary.ADTDictionary;
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

        example1.typeCheck(new ADTDictionary<>());
        ProgramState state1 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example1);
        IRepository repository1 = new Repository(state1, "logFile1.txt");
        IController controller1 = new Controller(repository1, true);

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

        example2.typeCheck(new ADTDictionary<>());
        ProgramState state2 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example2);
        IRepository repository2 = new Repository(state2, "logFile2.txt");
        IController controller2 = new Controller(repository2, true);

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

        example3.typeCheck(new ADTDictionary<>());
        ProgramState state3 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example3);
        IRepository repository3 = new Repository(state3, "logFile3.txt");
        IController controller3 = new Controller(repository3, true);

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

        example4.typeCheck(new ADTDictionary<>());
        ProgramState state4 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example4);
        IRepository repository4 = new Repository(state4, "logFile4.txt");
        IController controller4 = new Controller(repository4, true);

        // Example 5: Ref int v; new(v, 20); Ref Ref int a; new(a, v); Print(v); Print(a)
        // -- heap allocation example

        Statement example5 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );

        example5.typeCheck(new ADTDictionary<>());
        ProgramState state5 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example5);
        IRepository repository5 = new Repository(state5, "logFile5.txt");
        IController controller5 = new Controller(repository5, true);

        // Example 6: Ref int v; new(v, 20); Ref Ref int a; new(a, v); Print(ReadHeap(v)); Print(ReadHeap(ReadHeap(a)) + 5)
        // -- heap reading example

        Statement example6 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(
                                                        new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)),
                                                        '+')
                                                )
                                        )
                                )
                        )
                )
        );

        example6.typeCheck(new ADTDictionary<>());
        ProgramState state6 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example6);
        IRepository repository6 = new Repository(state6, "logFile6.txt");
        IController controller6 = new Controller(repository6, true);

        // Example 7: Ref int v; new(v, 20); print(ReadHeap(v)); WriteHeap(v, 30); Print(ReadHeap(v) + 5);
        // -- heap writing example

        Statement example7 = new CompoundStatement(
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

        example7.typeCheck(new ADTDictionary<>());
        ProgramState state7 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example7);
        IRepository repository7 = new Repository(state7, "logFile7.txt");
        IController controller7 = new Controller(repository7, true);

        // Example 8: int v; v = 4; (While (v > 0) (Print(v); v = v - 1); Print(v)
        // -- while statement example

        Statement example8 = new CompoundStatement(
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

        example8.typeCheck(new ADTDictionary<>());
        ProgramState state8 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example8);
        IRepository repository8 = new Repository(state8, "logFile8.txt");
        IController controller8 = new Controller(repository8, true);

        // Example 9: Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(ReadHeap(ReadHeap(a)))
        // -- garbage collector example --> all values reachable

        Statement example9 = new CompoundStatement(
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

        example9.typeCheck(new ADTDictionary<>());
        ProgramState state9 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example9);
        IRepository repository9 = new Repository(state9, "logFile9.txt");
        IController controller9 = new Controller(repository9, true);

        // Example 10: Ref int x; new(x, 10); Ref int y; new(y, 40); Ref Ref int r; new(r, y); new(y, 50); new(x, 60); Print(ReadHeap(ReadHeap(r)))
        // -- garbage collector example -> value 10 unreachable

        Statement example10 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "x"),
                new CompoundStatement(
                        new AllocateHeapStatement("x", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new IntType()), "y"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("y", new ValueExpression(new IntValue(40))),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "r"),
                                                new CompoundStatement(
                                                        new AllocateHeapStatement("r", new VariableExpression("y")),
                                                        new CompoundStatement(
                                                                new AllocateHeapStatement("y", new ValueExpression(new IntValue(50))),
                                                                new CompoundStatement(
                                                                        new AllocateHeapStatement("x", new ValueExpression(new IntValue(60))),
                                                                        new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("r"))))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        example10.typeCheck(new ADTDictionary<>());
        ProgramState state10 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example10);
        IRepository repository10 = new Repository(state10, "logFile10.txt");
        IController controller10 = new Controller(repository10, true);

        // Example 11: Ref int v; new(v, 100); Ref Ref int a; new(a, v); Ref Ref Ref int b; new(b, a); new(v, 200); new(a, v); new(b, a); Print(ReadHeap(ReadHeap(ReadHeap(b)))
        // -- garbage collector example -> 100, old a and old v unreachable

        Statement example11 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(100))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new ReferenceType(new IntType()))), "b"),
                                                new CompoundStatement(
                                                        new AllocateHeapStatement("b", new VariableExpression("a")),
                                                        new CompoundStatement(
                                                                new AllocateHeapStatement("v", new ValueExpression(new IntValue(200))),
                                                                new CompoundStatement(
                                                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                                                        new CompoundStatement(
                                                                                new AllocateHeapStatement("b", new VariableExpression("a")),
                                                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("b")))))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        example11.typeCheck(new ADTDictionary<>());
        ProgramState state11 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example11);
        IRepository repository11 = new Repository(state11, "logFile11.txt");
        IController controller11 = new Controller(repository11, true);

        // Example 12: int v; Ref int a; v = 10; new(a, 22); fork(WriteHeap(a, 30); v = 32; Print(v); Print(ReadHeap(a))); Print(v); Print(ReadHeap(a));

        Statement example12 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new ReferenceType(new IntType()), "a"),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                                                                                new PrintStatement(new VariableExpression("v"))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        example12.typeCheck(new ADTDictionary<>());
        ProgramState state12 = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example12);
        IRepository repository12 = new Repository(state12, "logFile12.txt");
        IController controller12 = new Controller(repository12, true);

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
        menu.addCommand(new RunExampleCommand("8", example8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", example9.toString(), controller9));
        menu.addCommand(new RunExampleCommand("10", example10.toString(), controller10));
        menu.addCommand(new RunExampleCommand("11", example11.toString(), controller11));
        menu.addCommand(new RunExampleCommand("12", example12.toString(), controller12));

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