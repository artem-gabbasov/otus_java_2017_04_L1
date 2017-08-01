package ru.otus;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 01.08.2017.
 * <p>
 */
public class SerialCustomThreadSorterTest extends ThreadSorterCommonTest {
    ThreadSorter<Integer> createSorter() {
        return new SerialCustomThreadSorter<Integer>();
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