package ru.otus.messagesystem.messagebuffers;

import java.util.concurrent.Semaphore;

/**
 * Created by Artem Gabbasov on 20.08.2017.
 */
public class SemaphoreManager {
    private Semaphore semaphore = null;
    private final int permitsCount;

    public SemaphoreManager(int permitsCount) {
        this.permitsCount = permitsCount;
    }

    public void release() {
        if (semaphore != null) {
            semaphore.release();
        }
    }

    public void acquire() throws InterruptedException {
        if (semaphore != null) {
            semaphore.acquire();
        }
    }

    public void clear() {
        semaphore = null;
    }

    public void init() {
        semaphore = new Semaphore(permitsCount);
    }
}
