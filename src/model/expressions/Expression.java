package model.expressions;

import model.adt.dictionary.IADTDictionary;
import model.expressions.exceptions.ExpressionException;
import model.values.Value;

public interface Expression {
    Value evaluate(IADTDictionary<String, Value> symbolsTable) throws ExpressionException;
    String toString();
}
