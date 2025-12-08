package model.adt.dictionary;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ADTSynchronizedDictionary<K, V> implements IADTDictionary<K, V> {
    private final Map<K, V> dictionary = new ConcurrentHashMap<>();

    @Override
    public void insert(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public synchronized V remove(K key) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.remove(key);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public synchronized V get(K key) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.get(key);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public synchronized V update(K key, V newValue) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.replace(key, newValue);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public boolean exists(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public synchronized Map<K, V> getMap() {
        return Map.copyOf(this.dictionary);
    }

    @Override
    public synchronized List<K> getKeys() {
        return new ArrayList<>(this.dictionary.keySet());
    }

    @Override
    public synchronized List<V> getValues() {
        return new ArrayList<>(this.dictionary.values());
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public int getSize() {
        return this.dictionary.size();
    }

    @Override
    public synchronized IADTDictionary<K, V> deepClone() {
        ADTSynchronizedDictionary<K, V> cloneDictionary = new ADTSynchronizedDictionary<>();

        for (K key : this.dictionary.keySet()) {
            cloneDictionary.insert(key, this.dictionary.get(key));
        }

        return cloneDictionary;
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }
}
