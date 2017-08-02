package ru.otus;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в двух разных потоках (уровень вложенности - двоичный логарифм количества потоков)
 */
public class LogarithmicCustomSorter<T extends Comparable<T>> extends ParallelCustomSorter<T> {
    /**
     *  Наибольший уровень, допускающий распараллеливание (равен двоичному логарифму максимального количества одновременно сортирующих потоков)
     *  В нашем задании подразумевается 4 одновременно сортирующих потока, т.е. максимальный уровень для распараллеливания равен 2
     */
    private static final int MAX_LEVEL = 2;

    @Override
    protected boolean isSerialPreferable(SortingTask<T> part) {
        return super.isSerialPreferable(part) ||
                part.getLevel() > MAX_LEVEL;
    }

    @Override
    protected Thread sortPartParallel(SortingTask<T> part) {
        Thread thread = new Thread(() -> part.perform());
        thread.start();
        return thread;
    }
}
