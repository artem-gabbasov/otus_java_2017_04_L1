package ru.otus;

import org.junit.Test;

/**
 * Created by Artem Gabbasov on 01.08.2017.
 * <p>
 */
public class SerialCustomSorterTest extends SorterCommonTest {
    @Override
    Sorter<Integer> createSorter() {
        return new SerialCustomSorter<Integer>();
    }
}