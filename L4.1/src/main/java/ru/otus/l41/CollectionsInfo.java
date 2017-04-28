package ru.otus.l41;

import java.util.Date;

/**
 * Created by Artem Gabbasov on 27.04.2017.
 *
 * Класс, используемый непосредственно для хранения информации по одному поколению
 */
public class CollectionsInfo {
    // количество сборок
    private int count;
    // суммарное время сборок (в миллисекундах)
    private long totalDuration;
    private String collectionType; // young/old

    public CollectionsInfo(String collectionType) {
        this.collectionType = collectionType;
        init();
    }

    public void init() {
        count = 0;
        totalDuration = 0;
    }

    public void registerCollection(long duration) {
        count++;
        totalDuration += duration;
    }

    public int getCount() {
        return count;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public String getCollectionType() {
        return collectionType;
    }

    @Override
    public String toString() {
        return "Type: " + collectionType + "; collections count: " + count + "; total duration: " + totalDuration + " ms (" + totalDuration / 1000 + " s " + totalDuration % 1000 + " ms)";
    }
}
