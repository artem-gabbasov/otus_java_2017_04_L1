package ru.otus.customsorter;

import ru.otus.SortingHelper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class SortingTask<T extends Comparable<T>> {
    /**
     * массив для сортировки
     */
    private final T[] array;

    /**
     * уровень вложенности вызова (двоичный логарифм текущего количества разбиений исходного массива)
     */
    private final int level;

    /**
     * Функция, сортирующая части массива по отдельности
     */
    private final Consumer<TasksPair> sortParts;

    public SortingTask(T[] array, int level, Consumer<TasksPair> sortParts) {
        this.array = array;
        this.level = level;
        this.sortParts = sortParts;
    }

    public T[] getArray() {
        return array;
    }

    public int getLevel() {
        return level;
    }

    public class TasksPair {
        private final SortingTask<T> left;
        private final SortingTask<T> right;

        public TasksPair(SortingTask<T> left, SortingTask<T> right) {
            this.left = left;
            this.right = right;
        }

        public SortingTask<T> getLeft() {
            return left;
        }

        public SortingTask<T> getRight() {
            return right;
        }
    }

    /**
     * Функция, выполняющая непосредственно сортировку массива
     */
    public void perform() {
        SortingHelper.performSorting(array, (arraysPair) -> {
            TasksPair pair = new TasksPair(
                    new SortingTask<T>(arraysPair.getLeft(), level + 1, sortParts),
                    new SortingTask<T>(arraysPair.getRight(), level + 1, sortParts)
            );

            sortParts.accept(pair);
        });
    }
}
