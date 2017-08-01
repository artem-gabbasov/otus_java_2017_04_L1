package ru.otus;

import java.util.Arrays;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public class StreamThreadSorter<T extends Comparable<T>> implements ThreadSorter<T> {
    public void sort(T[] array) {
        Arrays.parallelSort(array);
    }
}
