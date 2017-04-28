package ru.otus.l41;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Date;
import java.util.List;

/**
 * Created by Artem Gabbasov on 27.04.2017.
 */
public class GCMonitor {
    // Создаём два отдельных объекта:
    // для регистрации статистики за прошедшую минуту...
    private CollectionsManager currentMinuteCollections;
    // ... и для регистрации статистики с начала выполнения
    private CollectionsManager totalCollections;

    public GCMonitor() {
        // подписываемся на события
        installGCMonitoring();

        currentMinuteCollections = new CollectionsManager("Current minute collections");
        totalCollections = new CollectionsManager("Total collections");

        // устанавливаем таймер с интервалом в минуту
        int delay = 60_000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Здесь можно было сделать более атомарное получение лога по последней минуте (чтобы как можно раньше
                // сбросить счётчик), но это, как я понимаю, в данном задании учитывать необязательно

                // в первую очередь вытаскиваем информацию по последней минуте
                String cmcLog = currentMinuteCollections.toString();
                // и сбрасываем счётчик
                currentMinuteCollections.clear();

                // вывод лога тоже получился не thread-safe, но про потоки я пока не думал
                System.out.println("------------------------------------------\n" +
                        "Collections registered by " + new Date() + "\n" +
                        cmcLog + "\n" +
                        totalCollections + "\n" +
                        "Free memory: " + Runtime.getRuntime().freeMemory() + "\n" +
                        "------------------------------------------");

            }
        };
        new Timer(delay, taskPerformer).start();
    }

    private void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    // вытаскиваем из события info и передаём его в наши классы для регистрации статистики
                    currentMinuteCollections.registerCollection(info);
                    totalCollections.registerCollection(info);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
