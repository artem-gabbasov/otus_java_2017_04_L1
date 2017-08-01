package ru.otus;

import sun.rmi.runtime.Log;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в одном потоке
 */
public class SerialCustomSorter<T extends Comparable<T>> extends CustomSorter<T> {
    @Override
    protected void sortPart(SortingArguments<T> args, Consumer<SortingArguments<T>> sortingFunction) {
        Logger.getLogger(LOGGER_NAME).info("s");
        sortingFunction.accept(args);
    }
}
