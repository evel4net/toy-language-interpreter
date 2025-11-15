package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value {
    private final int heapAddress;
    private final Type locationType;

    public ReferenceValue(int heapAddress, Type locationType) {
        this.heapAddress = heapAddress;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.heapAddress;
    }

    @Override
//    public Type getType() {
//        return this.locationType;
//    }

    public Type getType() {
        return new ReferenceType(this.locationType);
    }
//    TODO return locationType or ReferenceType(locationType) ?

    @Override
    public boolean equals(Object another) {
        if (another instanceof ReferenceValue) {
            return (this.heapAddress == ((ReferenceValue) another).getAddress() && this.locationType == ((ReferenceValue) another).getType());
        }

        return false;
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(this.heapAddress, this.locationType);
    }

    @Override
    public String toString() {
        return Integer.toString(this.heapAddress) + " : " + this.locationType.toString(); // TODO ?
    }
}
