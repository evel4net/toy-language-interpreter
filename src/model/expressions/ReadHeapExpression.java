package model.expressions;

import exceptions.HeapAddressNotAssociated;
import exceptions.InvalidTypeException;
import exceptions.ProgramException;
import model.adt.dictionary.KeyNotDefinedException;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeapExpression implements Expression {
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolsTable symbolsTable, HeapTable heapTable) throws ProgramException {
        Value expressionValue = this.expression.evaluate(symbolsTable, heapTable);

        if (!(expressionValue instanceof ReferenceValue)) throw new InvalidTypeException("Expression evaluation is not of reference type.");

        ReferenceValue referenceValue = (ReferenceValue) expressionValue;
        int heapAddress = referenceValue.getAddress();

        if (!heapTable.existsAddress(heapAddress)) throw new HeapAddressNotAssociated(heapAddress);

        return heapTable.getValue(heapAddress);
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "ReadHeap(" + this.expression.toString() + ")";
    }
}
