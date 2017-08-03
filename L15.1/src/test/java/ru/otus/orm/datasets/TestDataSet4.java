package ru.otus.orm.datasets;

import javax.persistence.Column;

/**
 * Created by Artem Gabbasov on 01.07.2017.
 * <p>
 */
@SuppressWarnings("unused")
public class TestDataSet4 implements DataSet {
    @Column
    private int dummy;

    private int dummy2;

    @Column
    private int dummy3;

    @Override
    public long getId() {
        return 0;
    }
}
