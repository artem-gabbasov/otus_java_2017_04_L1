package ru.otus.db;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.cache.CacheEngine;
import ru.otus.cache.CacheEngineImpl;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 12.07.2017.
 * <p>
 */
public class DBServiceCachedTest extends DBServiceTestCommon {
    private CacheEngine cacheEngine;

    @Override
    public DBService createDBService() {
        cacheEngine = new CacheEngineImpl(1, 0, 0, true);
        return new DBServiceCachedImpl(connection, cacheEngine);
    }

    @BeforeClass
    public static void createTable() throws SQLException {
        DBServiceTestCommon.createTable();
    }

    @Before
    public void clear() throws SQLException {
        super.clear();
        cacheEngine = null;
    }

    @Test
    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        super.saveAndLoad();
    }

    @Test
    public void loadNotFound() throws SQLException, JPAException {
        super.loadNotFound();
    }

    @Test
    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        super.insertAndUpdate();
    }

    @Test
    public void cache() throws IllegalAccessException, SQLException, JPAException {
        DBService dbService = createDBService();

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        UserDataSet loadedUser = dbService.load(1, UserDataSet.class);

        // при сохранении объект должен был попасть в кеш
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 0;

        cacheEngine.clear();

        loadedUser = dbService.load(1, UserDataSet.class);

        // после очистки кеша тот же самый объект должен был загрузиться уже из базы
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 1;
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
        if (cacheEngine != null) {
            cacheEngine.dispose();
        }
    }
}
