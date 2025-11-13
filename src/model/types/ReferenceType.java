package model.types;

import model.values.Value;

public class ReferenceType implements Type {
    private final Type innerType;

    public ReferenceType(Type innerType) {
        this.innerType = innerType;
    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public Value getDefaultValue() {
        return null;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ReferenceType) {
            return this.innerType.equals(((ReferenceType) another).getInnerType());
        }

        return false;
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(this.innerType);
    }

    @Override
    public String toString() {
        return "Ref(" + this.innerType.toString() + ")";
    }
}
