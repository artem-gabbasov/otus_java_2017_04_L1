package ru.otus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class SortingHelper {
    public static class ArraysPair<T extends Comparable<T>> {
        private final T[] left, right;

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
     * @param array     исходный массив
     * @param <T>           тип элемента сортируемого массива
     * @return          пара массивов - левая и правая части исходного массива
     */
    private static <T extends Comparable<T>> ArraysPair<T> divide(T[] array) {
        int rightStart = array.length / 2;
        return new ArraysPair<>(
                Arrays.copyOfRange(array, 0, rightStart),
                Arrays.copyOfRange(array, rightStart, array.length)
        );
    }

    /**
     * Объединяет два отсортированных массива в единый отсортированный массив.
     * Не содержит проверку того, что входные массивы отсортированы
     * @param left  один из массивов для объединения (должен быть отсортирован)
     * @param right один из массивов для объединения (должен быть отсортирован)
     * @param <T>           тип элемента сортируемого массива
     * @return      объединённый отсортированный массив
     */
    private static <T extends Comparable<T>> T[] merge(T[] left, T[] right) {
        int resultLength = left.length + right.length;

        @SuppressWarnings("unchecked")
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

    /**
     * Функция, оборачивающая непосредственно сортировку массива
     * @param array         массив для сортировки
     * @param sortingStep   функция самой сортировки (одного шага сортировки, т.е. сортировки пары массивов)
     * @param <T>           тип элемента сортируемого массива
     */
    public static <T extends Comparable<T>> void performSorting(T[] array, Consumer<ArraysPair<T>> sortingStep) {
        if (array.length > 1) {
            ArraysPair<T> arraysPair = divide(array);

            sortingStep.accept(arraysPair);

            T[] result = merge(arraysPair.getLeft(), arraysPair.getRight());
            System.arraycopy(result, 0, array, 0, result.length);
        }
    }
}
