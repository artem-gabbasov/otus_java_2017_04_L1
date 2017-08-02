package ru.otus.parallelstream;

import ru.otus.Sorter;
import ru.otus.SorterCommonTest;
import ru.otus.parallelstream.StreamSorter;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public class StreamSorterTest extends SorterCommonTest {
    @Override
    public Sorter<Integer> createSorter() {
        return new StreamSorter<Integer>();
    }
}