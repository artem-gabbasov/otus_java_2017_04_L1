package ru.otus.cache;

import com.sun.istack.internal.NotNull;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    void clear();
}
