package ru.otus;

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
        assert getLogHandler().getSerialsCount() == 1 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sort() {
        super.sort();
        assert getLogHandler().getSerialsCount() == 19 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sortRepetitions() {
        super.sortRepetitions();
        assert getLogHandler().getSerialsCount() == 19 && getLogHandler().getParallelsCount() == 0;
    }

    @Override
    public void sortUnEven() {
        super.sortUnEven();
        assert getLogHandler().getSerialsCount() == 16 && getLogHandler().getParallelsCount() == 0;
    }
}