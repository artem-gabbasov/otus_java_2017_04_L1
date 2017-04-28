package ru.otus.l41;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.util.Date;

/**
 * Created by Artem Gabbasov on 27.04.2017.
 *
 * Класс, используемый для регистрации и вывода статистики по обоим поколениям объектов
 */
public class CollectionsManager {
    // отдельно ведём учёт статистики по разным поколениям объектов
    private CollectionsInfo young;
    private CollectionsInfo old;

    private String name;
    //запоминаем время начала для вывода в лог
    private Date startTime;

    public CollectionsManager(String name) {
        young = new CollectionsInfo("young");
        old = new CollectionsInfo("old");
        this.name = name;
        startTime = new Date();
    }

    public void registerCollection(GarbageCollectionNotificationInfo info) {
        switch (info.getGcAction()) {
            case "end of minor GC": {young.registerCollection(info.getGcInfo().getDuration()); break;}
            case "end of major GC": {old.registerCollection(info.getGcInfo().getDuration()); break;}
        }
    }

    // очистка счётчиков
    public void clear() {
        young.init();
        old.init();
        startTime = new Date();
    }

    public Date getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return name + " (started " + startTime + ")\n" +
                young + "\n" +
                old;
    }
}
