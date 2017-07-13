package ru.otus.db;

import ru.otus.cache.CacheEngine;
import ru.otus.cache.MyElement;
import ru.otus.datasets.DataSet;
import ru.otus.jpa.JPAException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 12.07.2017.
 * <p>
 */
public class DBServiceCachedImpl extends DBServiceImpl {
    private final CacheEngine cacheEngine;

    public DBServiceCachedImpl(Connection connection, CacheEngine cacheEngine) {
        super(connection);
        this.cacheEngine = cacheEngine;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) throws SQLException, IllegalAccessException, JPAException {
        //cacheEngine.put(new MyElement());
        super.save(dataSet);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, JPAException {
        //if () {
            //return cacheEngine.get();
        //} else {
            return super.load(id, clazz);
        //}
    }
}
