package model.statements.procedure_operations;

import exceptions.ProgramException;
import javafx.util.Pair;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.HeapTable;
import model.program_state.ProceduresTable;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.Type;
import model.values.Value;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CallProcedureStatement implements Statement {
    private final String procedureName;
    private final ArrayList<Expression> parameters;

    public CallProcedureStatement(String procedureName, ArrayList<Expression> parameters) {
        this.procedureName = procedureName;
        this.parameters = parameters;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        ProceduresTable proceduresTable = state.getProceduresTable();

        if (!proceduresTable.existsProcedure(this.procedureName)) throw new ProgramException("Procedure " + this.procedureName + " does not exist.");

        Pair<ArrayList<String>, Statement> procedureData = proceduresTable.getPair(this.procedureName);
        ArrayList<String> formalParameters = procedureData.getKey();

        SymbolsTable symbolsTable = state.getSymbolsTable();
        HeapTable heapTable = state.getHeapTable();
        ArrayList<Value> parametersValues = new ArrayList<>();
        for (Expression e : this.parameters) parametersValues.add(e.evaluate(symbolsTable, heapTable));

        SymbolsTable procedureSymbolsTable = new SymbolsTable();
        for (int i = 0; i < this.parameters.size(); i++) {
            String variable = formalParameters.get(i);
            Value value = parametersValues.get(i);

            procedureSymbolsTable.declareVariable(variable, value.getType());
            procedureSymbolsTable.updateVariableValue(variable, value);
        }

        state.addSymbolsTable(procedureSymbolsTable);
        state.getExecutionStack().push(new ReturnStatement());
        state.getExecutionStack().push(procedureData.getValue());

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new CallProcedureStatement(this.procedureName, this.parameters); // clone parameters
    }

    @Override
    public String toString() {
        return "Call " + this.procedureName + this.parameters.toString();
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
