package ru.otus.forkjoin;

import ru.otus.Sorter;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class ForkJoinSorter<T extends Comparable<T>> implements Sorter<T> {
    private final int threadsNumber;

    public ForkJoinSorter(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void sort(T[] array) {
        new ForkJoinPool(threadsNumber).invoke(new SortAction<>(array));
    }
}
