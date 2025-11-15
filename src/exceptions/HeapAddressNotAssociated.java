package exceptions;

public class HeapAddressNotAssociated extends ProgramException {
    public HeapAddressNotAssociated(int address) {
        super("Address " + address + " in heap is not associated to a value.");
    }
}
