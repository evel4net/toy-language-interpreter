package model.expressions;

import exceptions.ProgramException;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.values.Value;

public interface Expression {
    Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException;
    Expression deepCopy();
    String toString();
}
