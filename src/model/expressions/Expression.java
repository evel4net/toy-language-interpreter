package model.expressions;

import model.exceptions.ProgramException;
import model.program_state.SymbolsTable;
import model.values.Value;

public interface Expression {
    Value evaluate(SymbolsTable symbolsTable) throws ProgramException;
    String toString();
}
