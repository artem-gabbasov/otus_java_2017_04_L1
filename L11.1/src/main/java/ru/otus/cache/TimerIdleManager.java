package ru.otus.cache;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 18.07.2017.
 * <p>
 */
public class TimerIdleManager implements IdleManager {
    private final Supplier<TimerTask> taskSupplier;
    private final Consumer<TimerTask> scheduler;
    private TimerTask timerTask;

    public TimerIdleManager(Consumer<TimerTask> scheduler, Supplier<TimerTask> taskSupplier) {
        this.scheduler = scheduler;
        this.taskSupplier = taskSupplier;
    }

    @Override
    public void refresh() {
        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = taskSupplier.get();
        scheduler.accept(timerTask);
    }
}
