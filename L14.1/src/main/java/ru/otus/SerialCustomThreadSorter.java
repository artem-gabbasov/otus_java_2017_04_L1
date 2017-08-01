package ru.otus;

import java.util.function.BiFunction;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в одном потоке
 */
public class SerialCustomThreadSorter<T extends Comparable<T>> extends CustomThreadSorter<T> {
    @Override
    protected void sortParts(T[] left, T[] right, int level, BiFunction<T[], Integer, Void> sortingFunction) {
        sortingFunction.apply(left, level);
        sortingFunction.apply(right, level);
    }
}
