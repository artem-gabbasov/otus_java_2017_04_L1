package ru.otus;

import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public interface Sorter<T extends Comparable<T>> {
    String LOGGER_NAME = "SPCounterLogger";

    public void sort(T[] array);
}
