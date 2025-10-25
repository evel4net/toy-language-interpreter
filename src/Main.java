import controller.Controller;
import controller.IController;
import model.expressions.ArithmeticExpression;
import model.expressions.Expression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.program_state.ExecutionStack;
import model.program_state.Output;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import repository.IRepository;
import repository.Repository;

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

        // ---

        ExecutionStack executionStack = new ExecutionStack();
        SymbolsTable symbolsTable = new SymbolsTable();
        Output output = new Output();

        ProgramState state = new ProgramState(executionStack, symbolsTable, output, example1);

        IRepository repository = new Repository();
        repository.addProgramState(state);

        IController controller = new Controller(repository, true);
        controller.executeProgram();
    }
}