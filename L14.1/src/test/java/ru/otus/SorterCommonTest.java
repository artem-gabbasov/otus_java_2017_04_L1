package ru.otus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 31.07.2017.
 * <p>
 */
public abstract class SorterCommonTest {
    abstract Sorter<Integer> createSorter();

    @Before
    public void initLogger() {
        Logger logger = Logger.getLogger(Sorter.LOGGER_NAME);
        logger.setUseParentHandlers(false);
        while (logger.getHandlers().length > 0) {
            logger.removeHandler(logger.getHandlers()[0]);
        }
        logger.addHandler(new SPCounterHandler());
    }

    @Test(expected = NullPointerException.class)
    public void sortNull() {
        createSorter().sort(null);
    }

    @Test
    public void sortEmpty() {
        Integer[] array = new Integer[0];
        createSorter().sort(array);

        assert array.length == 0;
    }

    @Test
    public void sortTwoElements() {
        Integer[] array = new Integer[]{8, 5};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{5, 8});
    }

    @Test
    public void sort() {
        Integer[] array = {14, 3, 17, 12, 6, 19, 10, 7, 8, 15, 2, 11, 1, 16, 9, 5, 18, 13, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19});
    }

    @Test
    public void sortRepetitions() {
        Integer[] array = {14, 3, 17, 10, 6, 19, 10, 7, 8, 15, 2, 11, 1, 15, 9, 5, 18, 6, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 14, 15, 15, 17, 18, 19});
    }

    // 17 numbers
    @Test
    public void sortUnEven() {
        Integer[] array = {14, 3, 12, 6, 10, 7, 8, 15, 2, 11, 1, 16, 9, 5, 13, 0, 4};
        createSorter().sort(array);

        assert Arrays.equals(array, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
    }

    @After
    public void showStatistics() {
        Logger.getLogger(Sorter.LOGGER_NAME).getHandlers()[0].close();
    }
}
