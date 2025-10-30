package model.types;
import model.values.Value;

public interface Type {
    Value getDefaultValue();
    boolean equals(Object another);
    String toString();
}
