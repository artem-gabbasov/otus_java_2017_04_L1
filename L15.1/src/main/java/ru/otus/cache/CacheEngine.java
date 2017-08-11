package ru.otus.cache;

import ru.otus.observable.Listener;

/**
 * Created by tully.
 */
@SuppressWarnings("unused")
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

    /**
     * Добавляет listener, который отслеживает изменения параметров кэша
     * @param listener  listener, который отслеживает изменения параметров кэша
     * @return          true, если множество listener'ов было изменено. Иначе, false
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean addListener(Listener<Long> listener);

    /**
     * Удаляет listener, который отслеживает изменения параметров кэша
     * @param listener  listener, который отслеживает изменения параметров кэша
     * @return          true, если множество listener'ов было изменено. Иначе, false
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean removeListener(Listener<Long> listener);
}
