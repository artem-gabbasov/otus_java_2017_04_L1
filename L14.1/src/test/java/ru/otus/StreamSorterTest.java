package ru.otus;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public class StreamSorterTest extends SorterCommonTest {
    @Override
    Sorter<Integer> createSorter() {
        return new StreamSorter<Integer>();
    }
}