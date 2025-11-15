package controller;

import model.program_state.HeapTable;
import model.program_state.SymbolsTable;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {
    public Map<Integer, Value> unsafeCollect(List<Integer> symbolsTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolsTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Value> collect(List<Integer> symbolsTableAddresses, List<Integer> heapTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> (symbolsTableAddresses.contains(e.getKey()) || heapTableAddresses.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressesFromSymbolsTable(SymbolsTable symbolsTable) {
        return symbolsTable.getValues().stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).getAddress())
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressesFromHeapTable(HeapTable heapTable) {
        return heapTable.getValues().stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).getAddress())
                .collect(Collectors.toList());
    }
}
