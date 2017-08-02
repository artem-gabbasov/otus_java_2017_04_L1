package ru.otus;

import sun.rmi.runtime.Log;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в одном потоке
 */
public class SimpleCustomSorter<T extends Comparable<T>> extends CustomSorter<T> {
    @Override
    protected void sortParts(SortingTask<T>.TasksPair pair) {
        Logger.getLogger(LOGGER_NAME).info("s");
        pair.getLeft().perform();

        Logger.getLogger(LOGGER_NAME).info("s");
        pair.getRight().perform();
    }
}
