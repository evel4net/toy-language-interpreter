package model.adt.dictionary;

import java.util.List;
import java.util.Map;

public interface IADTDictionary<K, V> {
    void insert(K key, V value);
    V remove(K key) throws KeyNotDefinedException;
    V get(K key) throws KeyNotDefinedException;
    V update(K key, V newValue) throws KeyNotDefinedException;
    boolean exists(K key);

    Map<K, V> getMap();
    List<K> getKeys();
    List<V> getValues();

    boolean isEmpty();
    int getSize();

    IADTDictionary<K, V> deepClone();
    String toString();
}
