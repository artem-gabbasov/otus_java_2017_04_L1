package ru.otus.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

/**
 * Created by tully.
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    @SuppressWarnings("WeakerAccess")
    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        IdleManager idleManager = new EmptyIdleManager();

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                idleManager = new TimerIdleManager(
                        timerTask -> timer.schedule(timerTask, idleTimeMs),
                        () -> getTimerTask(key, idleElement -> idleElement.getCreationTime() + idleTimeMs)
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
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
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
        return maxElements;
    }

    @Override
    public int getElementsCount() {
        return (int)elements.values().stream()
                .filter(myElementSoftReference -> myElementSoftReference.get() != null)
                .count();
    }

    @Override
    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    @Override
    public long getIdleTimeMs() {
        return idleTimeMs;
    }

    @Override
    public boolean isEternal() {
        return isEternal;
    }
}
