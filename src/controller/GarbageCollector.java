package controller;

import exceptions.HeapAddressNotAssociated;
import model.adt.dictionary.KeyNotDefinedException;
import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {
    public Map<Integer, Value> collect(List<Integer> accessibleAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> accessibleAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAccessibleAddresses(List<SymbolsTable> symbolsTables, HeapTable heapTable) {
        return symbolsTables.stream()
                .flatMap(sym -> { return sym.getValues().stream(); })
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> (ReferenceValue) v)
                .flatMap(value -> { // replace each ReferenceValue with a stream containing all accessible addresses from heap
                    List<Integer> accessibleAddresses = new ArrayList<>();
                    Value nextValue;

                    while (true) {
                        if (value.getAddress() == 0) break;

                        accessibleAddresses.add(value.getAddress());

                        try {
                            nextValue = heapTable.getValue(value.getAddress());
                        } catch (KeyNotDefinedException e) {
                            throw new HeapAddressNotAssociated(value.getAddress());
                        }

                        if (!(nextValue instanceof ReferenceValue)) break;

                        value = (ReferenceValue) nextValue;
                    }

                    return accessibleAddresses.stream();
                })
                .collect(Collectors.toList());
    }

    // --- UNSAFE GARBAGE COLLECTOR

    public Map<Integer, Value> unsafeCollect(List<Integer> symbolsTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolsTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressesFromSymbolsTable(SymbolsTable symbolsTable) {
        return symbolsTable.getValues().stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).getAddress())
                .collect(Collectors.toList());
    }
}
