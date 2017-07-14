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
        // идентифицировать, наверное, уместнее всего по паре "класс датасета - ид датасета"
        // учесть ещё, что иды генерятся автоматически и не всегда известны в этот момент
        //cacheEngine.put();
        super.save(dataSet);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, JPAException {
        MyElement<DBServiceCacheKey, DataSet> element = cacheEngine.get(new DBServiceCacheKey(id, clazz));

        if (element != null) {
            DataSet result = element.getValue();
            try {
                return (T) result;
            } catch (ClassCastException e) {
                // здесь DataSet должен быть именно класса T, т.к. мы этот класс передаём для поиска в get
                ClassCastException e2 = new ClassCastException("An object of class " + result.getClass().getName() + " is retrieved by the CacheEngine. Expected class: " + clazz.getName());
                e2.initCause(e);
                throw e2;
            }
        } else {
            return super.load(id, clazz);
        }
    }
}
