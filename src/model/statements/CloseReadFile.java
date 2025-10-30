package model.statements;

import model.exceptions.InvalidTypeException;
import model.exceptions.ProgramException;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class CloseReadFile implements Statement {
    private final Expression file;

    public CloseReadFile(Expression file) {
        this.file = file;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value fileName = this.file.evaluate(state.getSymbolsTable());

        if (!(fileName.getType() instanceof StringType)) throw new InvalidTypeException("File name is not a string");

        state.getFileTable().closeAndRemoveFile((StringValue) fileName);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new CloseReadFile(this.file);
    }

    @Override
    public String toString() {
        return "CloseReadFile(" + this.file.toString()+")";
    }
}
