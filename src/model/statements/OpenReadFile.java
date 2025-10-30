package model.statements;

import model.exceptions.FileAlreadyExistsException;
import model.exceptions.InvalidTypeException;
import model.exceptions.ProgramException;
import model.expressions.Expression;
import model.program_state.FileTable;
import model.program_state.ProgramState;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class OpenReadFile implements Statement {
    private final Expression file;

    public OpenReadFile(Expression fileName) {
        this.file = fileName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        Value fileName = this.file.evaluate(state.getSymbolsTable());

        if (!(fileName.getType() instanceof StringType)) throw new InvalidTypeException("File name is not of string type.");

        FileTable filesTable = state.getFileTable();
        if (filesTable.existsFile((StringValue) fileName)) throw new FileAlreadyExistsException(((StringValue) fileName).getValue());

        filesTable.addFile((StringValue) fileName);

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new OpenReadFile(this.file);
    }

    @Override
    public String toString() {
        return "OpenReadFile(" + this.file.toString()+")";
    }
}
