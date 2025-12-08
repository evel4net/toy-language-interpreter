package model.expressions;

import exceptions.ProgramException;
import model.adt.dictionary.IADTDictionary;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException;
    Type typeCheck(IADTDictionary<String, Type> typeEnvironment) throws ProgramException;
    Expression deepCopy();
    String toString();
}
