package ru.otus.parallelstream;

import ru.otus.Sorter;

import java.util.Arrays;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public class StreamSorter<T extends Comparable<T>> implements Sorter<T> {
    public void sort(T[] array) {
        Arrays.parallelSort(array);
    }
}
