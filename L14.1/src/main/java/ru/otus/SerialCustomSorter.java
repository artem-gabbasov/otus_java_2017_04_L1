package ru.otus;

import java.util.function.BiFunction;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в одном потоке
 */
public class SerialCustomSorter<T extends Comparable<T>> extends CustomSorter<T> {
    @Override
    protected void sortPart(T[] part, int level, BiFunction<T[], Integer, Void> sortingFunction) {
        sortingFunction.apply(part, level);
    }
}
