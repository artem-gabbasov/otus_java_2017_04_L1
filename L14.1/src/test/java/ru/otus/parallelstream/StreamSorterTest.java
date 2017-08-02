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
    protected Sorter<Integer> createSorter() {
        return new StreamSorter<>();
    }
}