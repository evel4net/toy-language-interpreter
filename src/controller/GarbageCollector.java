package controller;

import model.values.ReferenceValue;
import model.values.Value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {
    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolsTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolsTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressesFromSymbolsTable(Collection<Value> symbolsTableValues) {
        return symbolsTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).getAddress())
                .collect(Collectors.toList());
    }
}
