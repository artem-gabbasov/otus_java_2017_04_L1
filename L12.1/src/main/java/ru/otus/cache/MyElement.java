package ru.otus.cache;

/**
 * Created by tully.
 */
@SuppressWarnings("unused")
public class MyElement<K, V> {
    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;
    private final IdleManager idleManager;

    public MyElement(K key, V value, IdleManager idleManager) {
        this.key = key;
        this.value = value;
        long currentTime = getCurrentTime();
        this.creationTime = currentTime;
        this.lastAccessTime = currentTime;
        this.idleManager = idleManager;
        idleManager.refresh();
    }

    @SuppressWarnings("WeakerAccess")
    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
        idleManager.refresh();
    }
}
