package ru.otus;

import sun.rmi.runtime.Log;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Абстрактный класс параллельного сортировщика
 */
public abstract class ParallelCustomSorter<T extends Comparable<T>> extends SerialCustomSorter<T> {
    /**
     * Пороговое значение количества элементов, меньше которого не имеет смысла делить на потоки (всё выполняется в едином потоке)
     */
    final static int PARALLEL_THRESHOLD = 4;

    @Override
    protected void sortPart(SortingArguments<T> args, Consumer<SortingArguments<T>> sortingFunction) {
        if (isSerialPreferable(args)) {
            super.sortPart(args, sortingFunction);
        } else {
            Logger.getLogger(LOGGER_NAME).info("p");
            sortPartParallel(args, sortingFunction);
        }
    }

    protected boolean isSerialPreferable(SortingArguments<T> args) {
        return args.getArray().length <= PARALLEL_THRESHOLD;
    }

    protected abstract void sortPartParallel(SortingArguments<T> args, Consumer<SortingArguments<T>> sortingFunction);
}
