package model.statements.file_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import exceptions.VariableNotDefinedException;
import model.adt.dictionary.IADTDictionary;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.statements.Statement;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;


public class ReadFileStatement implements Statement {
    private final Expression file;
    private final String variableName;

    public ReadFileStatement(Expression file, String variableName) {
        this.file = file;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        if(!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        Type variableType = symbolsTable.getVariableType(this.variableName);
        if (!(variableType instanceof IntType)) throw new InvalidTypeException("Variable in not of integer type.");

        Value fileName = this.file.evaluate(symbolsTable, state.getHeapTable());
        if (!(fileName instanceof StringValue)) throw new InvalidTypeException("File name is not of string type.");

        IntValue fileValue = state.getFileTable().readFile((StringValue) fileName);
        symbolsTable.updateVariableValue(this.variableName, fileValue);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        Type typeExpression = this.file.typeCheck(typeEnvironment);
        Type typeVariable = typeEnvironment.get(this.variableName);

        if (typeExpression.equals(new StringType())) {
            if (typeVariable.equals(new IntType())) return typeEnvironment;
            else throw new InvalidTypeException("Variable in not of integer type.");
        } else throw new InvalidTypeException("File name is not of string type.");
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(this.file.deepCopy(), this.variableName);
    }

    @Override
    public String toString() {
        return "ReadFile(" + this.file.toString()+", " + this.variableName +")";
    }

    @Override
    public String toPrettyString() {
        return this.toString() + ";";
    }
}
