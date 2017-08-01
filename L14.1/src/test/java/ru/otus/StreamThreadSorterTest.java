package ru.otus;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public class StreamThreadSorterTest extends ThreadSorterCommonTest {
    ThreadSorter<Integer> createSorter() {
        return new StreamThreadSorter<Integer>();
    }

    @Test
    public void sort() {
        super.sort();
    }

    @Test
    public void sortRepetitions() {
        super.sortRepetitions();
    }

    @Test
    public void sortUnEven() {
        super.sortUnEven();
    }
}