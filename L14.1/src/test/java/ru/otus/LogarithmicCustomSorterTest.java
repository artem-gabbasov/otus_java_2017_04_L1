package ru.otus;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class LogarithmicCustomSorterTest extends SorterCommonTest {
    final static int PARALLEL_THRESHOLD = 4;
    final static int MAX_LEVEL = 2;

    @Override
    Sorter<Integer> createSorter() {
        return new LogarithmicCustomSorter<Integer>(PARALLEL_THRESHOLD, MAX_LEVEL);
    }

    @Override
    public void sortTwoElements() {
        super.sortTwoElements();
        assert getLogHandler().getSerialsCount() == 2 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sort() {
        super.sort();
        assert getLogHandler().getSerialsCount() == 32 && getLogHandler().getParallelsCount() == 6;
    }

    @Override
    public void sortRepetitions() {
        super.sortRepetitions();
        assert getLogHandler().getSerialsCount() == 32 && getLogHandler().getParallelsCount() == 6;
    }

    @Override
    public void sortUnEven() {
        super.sortUnEven();
        assert getLogHandler().getSerialsCount() == 29 && getLogHandler().getParallelsCount() == 3;
    }
}