package ru.otus.datasets;

import ru.otus.datasets.DataSet;

import javax.persistence.Table;

/**
 * Created by Artem Gabbasov on 25.06.2017.
 * <p>
 */
@Table
public class TestDataSet2 implements DataSet {
    @Override
    public long getId() {
        return 0;
    }
}
