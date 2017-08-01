package ru.otus;

import java.lang.reflect.Array;
import java.util.Arrays;
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
        if (array.length > 1) {
            TasksPair pair = divide();

            sortParts.accept(pair);

            T[] result = merge(pair.getLeft().getArray(), pair.getRight().getArray());
            System.arraycopy(result, 0, array, 0, result.length);
        }
    }

    /**
     * Делит массив на две равные части (в случае чётного количества элементов).
     * Если массив содержит нечётное количество элементов, то больше элементов попадает в правую часть
     * @return      пара задач для сортировки - для левой и правой частей массива
     */
    private TasksPair divide() {
        int rightStart = array.length / 2;
        return new TasksPair(
                new SortingTask<T>(Arrays.copyOfRange(array, 0, rightStart), level + 1, sortParts),
                new SortingTask<T>(Arrays.copyOfRange(array, rightStart, array.length), level + 1, sortParts));
    }

    /**
     * Объединяет два отсортированных массива в единый отсортированный массив.
     * Не содержит проверку того, что входные массивы отсортированы
     * @param left  один из массивов для объединения (должен быть отсортирован)
     * @param right один из массивов для объединения (должен быть отсортирован)
     * @return      объединённый отсортированный массив
     */
    private T[] merge(T[] left, T[] right) {
        int resultLength = left.length + right.length;
        T[] result = (T[]) Array.newInstance(left.getClass().getComponentType(), resultLength);

        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < resultLength; i++) {
            if (leftIndex >= left.length) {
                result[i] = right[rightIndex++];
            } else
            if (rightIndex >= right.length) {
                result[i] = left[leftIndex++];
            } else {
                result[i] = left[leftIndex].compareTo(right[rightIndex]) < 0 ? left[leftIndex++] : right[rightIndex++];
            }
        }
        return result;
    }
}
