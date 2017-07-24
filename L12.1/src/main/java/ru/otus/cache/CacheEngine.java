package ru.otus.cache;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    MyElement<K, V> get(K key);

    int getMaxElements();

    int getElementsCount();

    long getLifeTimeMs();

    long getIdleTimeMs();

    boolean isEternal();

    int getHitCount();

    int getMissCount();

    void dispose();

    void clear();
}
