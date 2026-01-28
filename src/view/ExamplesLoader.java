package view;

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

import java.util.ArrayList;
import java.util.List;

public class ExamplesLoader {
    private List<String> examples = new ArrayList<>();
    private List<IController> controllers = new ArrayList<>();

    public ExamplesLoader() {
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
        this.addExample(example1);

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
        this.addExample(example2);

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
        this.addExample(example3);

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
        this.addExample(example4);

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
        this.addExample(example5);

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
        this.addExample(example6);
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
        this.addExample(example7);

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
        this.addExample(example8);

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
        this.addExample(example9);

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
        this.addExample(example10);

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
        this.addExample(example11);

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
        this.addExample(example12);

        // Example 13
        Statement example13 = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "x"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new IntType(), "y"),
                                new CompoundStatement(
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(
                                                new RepeatUntilStatement(
                                                        new CompoundStatement(
                                                                new ForkStatement(new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-'))
                                                                )),
                                                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '+'))
                                                        ),
                                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")
                                                ),
                                                new CompoundStatement(
                                                        new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                        new CompoundStatement(
                                                                new NoOperationStatement(),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                                                                        new CompoundStatement(
                                                                                new NoOperationStatement(),
                                                                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), '*'))
                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )
        );
        this.addExample(example13);


    }

    private void addExample(Statement example) {
        example.typeCheck(new ADTDictionary<>());

        ProgramState state = new ProgramState(new ExecutionStack(), new SymbolsTable(), new Output(), new FileTable(), new HeapTable(), example);
        IRepository repository = new Repository(state, "logFile_" + Integer.toString(this.examples.size() + 1) + ".txt");
        IController controller = new Controller(repository, false);

        this.examples.addLast(example.toString());
        this.controllers.addLast(controller);
    }

    public String getString(int index) {
        return this.examples.get(index);
    }

    public IController getController(int index) {
        return this.controllers.get(index);
    }

    public List<String> getAllStrings() {
        return List.copyOf(this.examples);
    }

    public List<IController> getAllControllers() {
        return List.copyOf(this.controllers);
    }
}
