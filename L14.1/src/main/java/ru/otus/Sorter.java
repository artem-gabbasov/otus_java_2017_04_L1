package ru.otus;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public interface Sorter<T extends Comparable<T>> {
    String LOGGER_NAME = "SPCounterLogger";

    void sort(T[] array);
}
