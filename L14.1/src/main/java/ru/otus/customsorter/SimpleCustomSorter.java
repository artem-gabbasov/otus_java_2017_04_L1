package ru.otus.customsorter;

import ru.otus.Sorter;

import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в одном потоке
 */
public class SimpleCustomSorter<T extends Comparable<T>> extends CustomSorter<T> {
    @Override
    protected void sortParts(SortingTask<T>.TasksPair pair) {
        Logger.getLogger(Sorter.LOGGER_NAME).info("s");
        pair.getLeft().perform();

        Logger.getLogger(Sorter.LOGGER_NAME).info("s");
        pair.getRight().perform();
    }
}
