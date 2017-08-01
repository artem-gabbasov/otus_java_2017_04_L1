package ru.otus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Created by Artem Gabbasov on 01.08.2017.
 * Класс, выполняющий сортировку (mergesort) с использованием TemplateMethod (sortParts)
 */
public abstract class CustomSorter<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(T[] array) {
        doSort(array, 0);
    }

    private class ArraysPair {
        private final T[] left;
        private final T[] right;

        public ArraysPair(T[] left, T[] right) {
            this.left = left;
            this.right = right;
        }

        public T[] getLeft() {
            return left;
        }

        public T[] getRight() {
            return right;
        }
    }

    /**
     * Делит массив на две равные части (в случае чётного количества элементов).
     * Если массив содержит нечётное количество элементов, то больше элементов попадает в правую часть
     * @param array массив, который следует разделить
     * @return      пара массивов - левая и правая части
     */
    private ArraysPair divide(T[] array) {
        int rightStart = array.length / 2;
        return new ArraysPair(Arrays.copyOfRange(array, 0, rightStart), Arrays.copyOfRange(array, rightStart, array.length));
    }

    /**
     * Функция, выполняющая непосредственно сортировку массива
     * @param array массив для сортировки
     * @param level уровень вложенности вызова (двоичный логарифм текущего количества разбиений исходного массива)
     */
    private void doSort(T[] array, int level) {
        if (array.length > 1) {
            ArraysPair pair = divide(array);

            sortParts(pair.getLeft(), pair.getRight(), level + 1, (arr, lev) -> {doSort(arr, lev); return null;});

            T[] result = merge(pair.getLeft(), pair.getRight());
            System.arraycopy(result, 0, array, 0, result.length);
        }
    }

    /**
     * Функция, сортирующая части массива по отдельности
     * @param left  левая часть массива
     * @param right правая часть массива
     * @param level уровень вложенности вызова (двоичный логарифм текущего количества разбиений исходного массива)
     */
    protected abstract void sortParts(T[] left, T[] right, int level, BiFunction<T[], Integer, Void> sortingFunction);

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
