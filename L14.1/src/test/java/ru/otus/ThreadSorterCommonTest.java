package ru.otus;

import ru.otus.ThreadSorter;

import java.util.Arrays;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public abstract class ThreadSorterCommonTest {
    abstract ThreadSorter<Integer> createSorter();

    public void sort() {
        Integer[] array = {3, 6, 2, 1, 5, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{1, 2, 3, 4, 5, 6});
    }
}
