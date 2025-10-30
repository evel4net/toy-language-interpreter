package model.statements;

import model.adt.dictionary.KeyNotDefinedException;
import model.exceptions.FileNotFoundException;
import model.exceptions.InvalidTypeException;
import model.exceptions.ProgramException;
import model.exceptions.VariableNotDefinedException;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.program_state.SymbolsTable;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Statement {
    private final Expression file;
    private final String variableName;

    public ReadFile(Expression file, String variableName) {
        this.file = file;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        SymbolsTable symbolsTable = state.getSymbolsTable();
        if(!symbolsTable.isVariableDefined(this.variableName)) throw new VariableNotDefinedException(this.variableName);

        Type variableType = symbolsTable.getVariableType(this.variableName);
        if (!(variableType instanceof IntType)) throw new InvalidTypeException("Variable in not of integer type.");

        Value fileName = this.file.evaluate(symbolsTable);
        if (!(fileName instanceof StringValue)) throw new InvalidTypeException("File name is not of string type.");

        try {
            BufferedReader fileReader = state.getFileTable().getReader((StringValue) fileName);
            String line = fileReader.readLine();

            IntValue lineValue;
            if (line == null) lineValue = new IntValue(0);
            else lineValue = new IntValue(Integer.parseInt(line));

            symbolsTable.updateVariableValue(this.variableName, lineValue);
        } catch (KeyNotDefinedException | IOException e) {
            throw new FileNotFoundException(((StringValue) fileName).getValue());
        }

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new ReadFile(this.file, this.variableName);
    }

    @Override
    public String toString() {
        return "ReadFile(" + this.file.toString()+", " + this.variableName +")";
    }
}
