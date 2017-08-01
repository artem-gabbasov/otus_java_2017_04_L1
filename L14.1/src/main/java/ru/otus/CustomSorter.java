package ru.otus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 01.08.2017.
 * Класс, выполняющий сортировку (mergesort) с использованием TemplateMethod (sortParts)
 */
public abstract class CustomSorter<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(T[] array) {
        new SortingTask<>(array, 0, (pair) -> sortParts(pair)).perform();
    }

    /**
     * Функция, сортирующая части массива по отдельности
     * @param pair      задачи для осртировки левой и правой частей массива
     */
    protected abstract void sortParts(SortingTask<T>.TasksPair pair);
}
