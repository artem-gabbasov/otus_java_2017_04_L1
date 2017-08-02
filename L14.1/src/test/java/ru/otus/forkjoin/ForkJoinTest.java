package ru.otus.forkjoin;

import ru.otus.Sorter;
import ru.otus.SorterCommonTest;

/**
 * Created by Artem Gabbasov on 03.08.2017.
 */
@SuppressWarnings("FieldCanBeLocal")
public class ForkJoinTest extends SorterCommonTest {
    private final int THREADS_NUMBER = 4;

    @Override
    protected Sorter<Integer> createSorter() {
        return new ForkJoinSorter<>(THREADS_NUMBER);
    }
}
