package ru.otus.db;

import ru.otus.datasets.DataSet;

/**
 * Created by Artem Gabbasov on 15.07.2017.
 * <p>
 */
public class DBServiceCacheKey {
    private long id;
    private Class<? extends DataSet> clazz;

    public DBServiceCacheKey(long id, Class<? extends DataSet> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends DataSet> clazz) {
        this.clazz = clazz;
    }
}
