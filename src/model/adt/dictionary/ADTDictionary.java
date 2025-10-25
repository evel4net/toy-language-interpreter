package model.adt.dictionary;

import java.util.*;

public class ADTDictionary<K, V> implements IADTDictionary<K, V> {
    private final Map<K, V> dictionary = new HashMap<>();

    @Override
    public void insert(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public V remove(K key) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.remove(key);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public V get(K key) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.get(key);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public V update(K key, V newValue) throws KeyNotDefinedException {
        if (this.exists(key)) return this.dictionary.replace(key, newValue);

        throw new KeyNotDefinedException(key.toString());
    }

    @Override
    public boolean exists(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public Map<K, V> getMap() {
        return this.dictionary;
    }

    @Override
    public List<K> getKeys() {
        return new ArrayList<>(this.dictionary.keySet());
    }

    @Override
    public List<V> getValues() {
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
    public String toString() {
        return this.dictionary.toString();
    }
}
