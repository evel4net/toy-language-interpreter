package model.expressions;

import exceptions.ProgramException;
import model.program_state.SymbolsTable;
import model.values.Value;

public interface Expression {
    Value evaluate(SymbolsTable symbolsTable) throws ProgramException;
    Expression deepCopy();
    String toString();
}
