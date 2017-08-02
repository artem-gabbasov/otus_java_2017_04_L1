package ru.otus.customsorter;

import ru.otus.Sorter;

import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Абстрактный класс параллельного сортировщика
 */
public abstract class ParallelCustomSorter<T extends Comparable<T>> extends SimpleCustomSorter<T> {
    /**
     * Пороговое значение количества элементов, меньше которого не имеет смысла делить на потоки (всё выполняется в едином потоке)
     */
    protected final int parallelThreshold;

    public ParallelCustomSorter(int parallelThreshold) {
        this.parallelThreshold = parallelThreshold;
    }

    @Override
    protected void sortParts(SortingTask<T>.TasksPair pair) {
        Thread threadLeft = startThread(pair.getLeft());
        Thread threadRight = startThread(pair.getRight());

        try {
            if (threadLeft != null) {
                threadLeft.join();
            } else {
                pair.getLeft().perform();
            }

            if (threadRight != null) {
                threadRight.join();
            } else {
                pair.getRight().perform();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected Thread startThread(SortingTask<T> part) {
        if (isSerialPreferable(part)) {
            Logger.getLogger(Sorter.LOGGER_NAME).info("s");
            return null;
        } else {
            Logger.getLogger(Sorter.LOGGER_NAME).info("p");
            return sortPartParallel(part);
        }
    }

    protected boolean isSerialPreferable(SortingTask<T> part) {
        return part.getArray().length <= parallelThreshold;
    }

    protected abstract Thread sortPartParallel(SortingTask<T> part);
}
