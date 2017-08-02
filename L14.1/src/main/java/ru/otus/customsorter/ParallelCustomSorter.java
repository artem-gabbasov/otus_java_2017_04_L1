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
    @SuppressWarnings("WeakerAccess")
    protected final int parallelThreshold;

    ParallelCustomSorter(int parallelThreshold) {
        this.parallelThreshold = parallelThreshold;
    }

    /**
     * Сортировка левого и правого подмассивов
     * @param pair  пара подмассивов (левый и правый)
     */
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

    /**
     * Метод, возвращающий объект (запущенного) потока, если выполнение идёт параллельно
     * @param part  часть массива для сортировки
     * @return  Если выполнение пойдёт параллельно, то объект нового (запущенного) потока
     *          Если выполнение пойдёт последовательно, то null
     */
    @SuppressWarnings("WeakerAccess")
    protected Thread startThread(SortingTask<T> part) {
        if (isSerialPreferable(part)) {
            Logger.getLogger(Sorter.LOGGER_NAME).info("s");
            return null;
        } else {
            Logger.getLogger(Sorter.LOGGER_NAME).info("p");
            return sortPartParallel(part);
        }
    }

    /**
     * Проверяет, будет ли уместнее отсортировать подмассив последовательно без запуска нового потока
     * @param part  подмассив для сортировки
     * @return  Если сортировать подмассив уместнее последовательно, то true
     *          Иначе, false
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean isSerialPreferable(SortingTask<T> part) {
        return part.getArray().length <= parallelThreshold;
    }

    /**
     * Запускает сортировку подмассива в отдельном потоке и возвращает его
     * @param part  подмассив для сортировки
     * @return      запущенный на выполнение поток, в котором выполняется сортировка
     */
    protected abstract Thread sortPartParallel(SortingTask<T> part);
}
