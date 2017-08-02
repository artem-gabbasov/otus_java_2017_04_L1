package ru.otus.customsorter;

import ru.otus.Sorter;

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
