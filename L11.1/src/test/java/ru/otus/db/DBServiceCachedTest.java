package ru.otus.db;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.cache.CacheEngine;
import ru.otus.cache.CacheEngineImpl;
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
    public void cache() {
        assert false;
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
        if (cacheEngine != null) {
            cacheEngine.dispose();
        }
    }
}
