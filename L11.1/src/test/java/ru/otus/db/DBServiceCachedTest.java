package ru.otus.db;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.cache.CacheEngineImpl;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 12.07.2017.
 * <p>
 */
public class DBServiceCachedTest extends DBServiceTestCommon {
    @Override
    public DBService createDBService() {
        return new DBServiceCachedImpl(connection, new CacheEngineImpl(1, 0, 0, true));
    }

    @BeforeClass
    public static void createTable() throws SQLException {
        DBServiceTestCommon.createTable();
    }

    @Before
    public void clear() throws SQLException {
        super.clear();
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

    @After
    public void rollback() throws SQLException {
        super.rollback();
    }
}
