package ru.otus.customsorter;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 * Класс, сортирующий части массива в двух разных потоках (уровень вложенности - двоичный логарифм количества потоков)
 */
public class BinaryCustomSorter<T extends Comparable<T>> extends ParallelCustomSorter<T> {
    /**
     *  Наибольший уровень, допускающий распараллеливание (равен двоичному логарифму максимального количества одновременно сортирующих потоков)
     *  В нашем задании подразумевается 4 одновременно сортирующих потока, т.е. максимальный уровень для распараллеливания равен 2
     */
    private final int maxLevel;

    public BinaryCustomSorter(int parallelThreshold, int maxLevel) {
        super(parallelThreshold);
        this.maxLevel = maxLevel;
    }

    @Override
    protected boolean isSerialPreferable(SortingTask<T> part) {
        return super.isSerialPreferable(part) ||
                part.getLevel() > maxLevel;
    }

    @Override
    protected Thread sortPartParallel(SortingTask<T> part) {
        Thread thread = new Thread(() -> part.perform());
        thread.start();
        return thread;
    }
}
