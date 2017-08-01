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
        Integer[] array = {14, 3, 17, 12, 6, 19, 10, 7, 8, 15, 2, 11, 1, 16, 9, 5, 18, 13, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19});
    }

    public void sortRepetitions() {
        Integer[] array = {14, 3, 17, 10, 6, 19, 10, 7, 8, 15, 2, 11, 1, 15, 9, 5, 18, 6, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 14, 15, 15, 17, 18, 19});
    }

    // 17 numbers
    public void sortUnEven() {
        Integer[] array = {14, 3, 12, 6, 10, 7, 8, 15, 2, 11, 1, 16, 9, 5, 13, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
    }
}
