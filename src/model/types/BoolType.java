package model.types;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type {
    @Override
    public Value getDefaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof BoolType);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString() { return "bool"; }
}
