package ru.otus.orm.datasets;

import javax.persistence.Id;

/**
 * Created by Artem Gabbasov on 02.07.2017.
 * <p>
 */
@SuppressWarnings("unused")
public class TestDataSet5 implements DataSet {
    @Override
    public long getId() {
        return 0;
    }

    @Id
    private long id1;

    @Id
    private long id2;
}
