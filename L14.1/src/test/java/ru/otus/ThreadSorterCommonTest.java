package ru.otus;

import ru.otus.ThreadSorter;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public abstract class ThreadSorterCommonTest {
    abstract ThreadSorter<Integer> createSorter();

    public void sort() {
        Integer[] array = {3, 6, 2, 1, 5, 4};
        createSorter().sort(array);

        assert array.equals(new Integer[]{1, 2, 3, 4, 5, 6});
    }
}
