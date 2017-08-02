package ru.otus.customsorter;

import ru.otus.Sorter;
import ru.otus.SorterCommonTest;
import ru.otus.customsorter.SimpleCustomSorter;

/**
 * Created by Artem Gabbasov on 01.08.2017.
 * <p>
 */
public class SimpleCustomSorterTest extends SorterCommonTest {
    @Override
    Sorter<Integer> createSorter() {
        return new SimpleCustomSorter<Integer>();
    }

    @Override
    public void sortTwoElements() {
        super.sortTwoElements();
        assert getLogHandler().getSerialsCount() == 2 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sort() {
        super.sort();
        assert getLogHandler().getSerialsCount() == 38 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sortRepetitions() {
        super.sortRepetitions();
        assert getLogHandler().getSerialsCount() == 38 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sortUnEven() {
        super.sortUnEven();
        assert getLogHandler().getSerialsCount() == 32 && getLogHandler().getParallelsCount() == 0;
    }
}