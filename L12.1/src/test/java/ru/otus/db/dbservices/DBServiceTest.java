package ru.otus.db.dbservices;

import org.junit.*;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
@SuppressWarnings("EmptyMethod")
public class DBServiceTest extends DBServiceTestCommon {
    @Override
    public DBService createDBService() {
        return new DBServiceImpl(connection);
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
