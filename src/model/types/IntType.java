package model.types;

import model.values.IntValue;
import model.values.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Object another) {
        return (another instanceof IntType);
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() { return "int"; }
}
