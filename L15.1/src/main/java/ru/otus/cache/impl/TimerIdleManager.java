package ru.otus.cache.impl;

import ru.otus.cache.IdleManager;

import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 18.07.2017.
 * <p>
 * Реализация интерфейса {@link IdleManager}, которая удаляет idle элементы по таймеру
 */
public class TimerIdleManager implements IdleManager {
    private final Supplier<TimerTask> taskSupplier;
    private final Consumer<TimerTask> scheduler;
    private TimerTask timerTask;

    /**
     * Конструктор, принимающий объекты для создания и регистрации задач таймера. Нужны именно оба объекта, поскольку надо промежуточно сохранить саму задачу для возможности её отмены
     * @param scheduler     объект, который размещает у себя и запускает задачи удаления элементов
     * @param taskSupplier  объект, который генерирует задачи удаления элементов
     */
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
