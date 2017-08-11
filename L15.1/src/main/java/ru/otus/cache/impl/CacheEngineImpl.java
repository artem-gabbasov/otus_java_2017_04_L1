package ru.otus.cache.impl;

import ru.otus.cache.CacheEngine;
import ru.otus.cache.IdleManager;
import ru.otus.cache.MyElement;
import ru.otus.observable.Listener;
import ru.otus.observable.ObservableVariable;
import ru.otus.observable.ObserverManager;
import ru.otus.observable.ObserverManagerImpl;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

/**
 * Created by tully.
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final ObservableVariable<Long> maxElements;
    private final ObservableVariable<Long> lifeTimeMs;
    private final ObservableVariable<Long> idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private final ObservableVariable<Long> hit;
    private final ObservableVariable<Long> miss;

    private final ObservableVariable<Long> elementsCountSnapshot;

    private final ObserverManager<Long> observerManager;

    @SuppressWarnings("WeakerAccess")
    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        observerManager = new ObserverManagerImpl<>();

        this.maxElements = observerManager.createNewObservableVariable("maxElements", (long)maxElements);
        this.lifeTimeMs = observerManager.createNewObservableVariable("lifeTime", lifeTimeMs > 0 ? lifeTimeMs : 0);
        this.idleTimeMs = observerManager.createNewObservableVariable("idleTime", idleTimeMs > 0 ? idleTimeMs : 0);
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;

        hit = observerManager.createNewObservableVariable("hitCount", 0L);
        miss = observerManager.createNewObservableVariable("missCount", 0L);
        elementsCountSnapshot = observerManager.createNewObservableVariable("elementsCount", 0L);
    }

    public void put(K key, V value) {
        if (elements.size() == maxElements.getValue()) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        IdleManager idleManager = new EmptyIdleManager();

        if (!isEternal) {
            long lifeTimeMsVal = lifeTimeMs.getValue();
            if (lifeTimeMsVal != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMsVal);
                timer.schedule(lifeTimerTask, lifeTimeMsVal);
            }
            long idleTimeMsVal = idleTimeMs.getValue();
            if (idleTimeMsVal != 0) {
                idleManager = new TimerIdleManager(
                        timerTask -> timer.schedule(timerTask, idleTimeMsVal),
                        () -> getTimerTask(key, idleElement -> idleElement.getCreationTime() + idleTimeMsVal)
                );
            }
        }

        elements.put(key, new SoftReference<>(new MyElement<>(key, value, idleManager)));
    }

    private MyElement<K, V> getFromMap(K key) {
        SoftReference<MyElement<K, V>> ref = elements.get(key);
        if (ref != null) return ref.get();
        return null;
    }

    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = getFromMap(key);
        if (element != null) {
            hit.setValue(hit.getValue() + 1);
            element.setAccessed();
        } else {
            miss.setValue(miss.getValue() + 1);
        }
        return element;
    }

    public int getHitCount() {
        return (int)(long)hit.getValue();
    }

    public int getMissCount() {
        return (int)(long)miss.getValue();
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    @Override
    public void clear() {
        elements.clear();
        dispose();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> checkedElement = getFromMap(key);
                if (checkedElement == null ||
                        isT1BeforeT2(timeFunction.apply(checkedElement), System.currentTimeMillis())) {
                    elements.remove(key);
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public int getMaxElements() {
        return (int)(long)maxElements.getValue();
    }

    @Override
    public int getElementsCount() {
        elementsCountSnapshot.setValue(
            elements.values().stream()
                    .filter(myElementSoftReference -> myElementSoftReference.get() != null)
                    .count()
        );
        return (int)(long) elementsCountSnapshot.getValue();
    }

    @Override
    public long getLifeTimeMs() {
        return lifeTimeMs.getValue();
    }

    @Override
    public long getIdleTimeMs() {
        return idleTimeMs.getValue();
    }

    @Override
    public boolean isEternal() {
        return isEternal;
    }

    @Override
    public boolean addListener(Listener<Long> listener) {
        return observerManager.addListener(listener);
    }

    @Override
    public boolean removeListener(Listener<Long> listener) {
        return observerManager.removeListener(listener);
    }
}
