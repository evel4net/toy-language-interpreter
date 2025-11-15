package model.statements.file_operations;

import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.expressions.Expression;
import model.program_state.ProgramState;
import model.statements.Statement;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class CloseReadFileStatement implements Statement {
    private final Expression file;

    public CloseReadFileStatement(Expression file) {
        this.file = file;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value fileName = this.file.evaluate(state.getSymbolsTable(), );

        if (!(fileName.getType() instanceof StringType)) throw new InvalidTypeException("File name is not a string");

        state.getFileTable().closeFile((StringValue) fileName);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new CloseReadFileStatement(this.file.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseReadFile(" + this.file.toString()+")";
    }
}
