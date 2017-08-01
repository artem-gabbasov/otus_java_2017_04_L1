package ru.otus;

import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class SortingArguments<T extends Comparable<T>> {
    private final T[] array;
    private final int level;

    public SortingArguments(T[] array, int level) {
        this.array = array;
        this.level = level;
    }

    public T[] getArray() {
        return array;
    }

    public int getLevel() {
        return level;
    }
}
