package ru.otus.forkjoin;

import ru.otus.SortingHelper;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class SortAction<T extends Comparable<T>> extends RecursiveAction {
    private final T[] array;

    public SortAction(T[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        SortingHelper.performSorting(array, (arraysPair) -> {
            SortAction<T> leftAction = new SortAction<T>(arraysPair.getLeft());
            SortAction<T> rightAction = new SortAction<T>(arraysPair.getRight());
            leftAction.fork();
            rightAction.fork();
            leftAction.join();
            rightAction.join();
        });
    }
}
