package model.statements.procedure_operations;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.ProgramState;
import model.statements.Statement;
import model.types.Type;

import java.util.List;

public class DeclareProcedureStatement implements Statement {
    private final String name;
    private final List<String> formalParameters;
    private final Statement body;

    public DeclareProcedureStatement(String name, List<String> formalParameters, Statement body) {
        this.name = name;
        this.formalParameters = formalParameters;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ProgramException {
        if (state.getProceduresTable().existsProcedure(this.name)) throw new ProgramException("Procedure " + this.name + " already exists in ProceduresTable.");

        state.getProceduresTable().addNewProcedure(this.name, this.formalParameters, this.body);

        return null;
    }

    @Override
    public IADTDictionary<String, Type> typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new DeclareProcedureStatement(this.name, this.formalParameters, this.body.deepCopy());
    }

    @Override
    public String toString() {
        return "Procedure " + this.name + this.formalParameters.toString() + " (" + this.body.toString() + ")";
    }

    @Override
    public String toPrettyString() {
        return "Procedure" + this.formalParameters.toString() + " (\n\t" + this.body.toPrettyString() + "\n);";
    }
}
