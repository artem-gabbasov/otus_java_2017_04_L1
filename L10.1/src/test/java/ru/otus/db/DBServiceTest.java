package ru.otus.db;

import org.junit.*;
import ru.otus.jpa.JPAException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public class DBServiceTest extends DBServiceTestCommon {
    @BeforeClass
    public static void createTable() throws SQLException {
        try (Connection createConnection = ConnectionHelper.getConnection(ConnectionHelperTest.url)) {
            createConnection.createStatement().execute("CREATE TABLE IF NOT EXISTS db_example.users (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255), age INT(3) NOT NULL DEFAULT 0, PRIMARY KEY (id));");
            createConnection.commit();
        }
    }

    @Before
    public void clear() throws SQLException {
        super.clear();
    }

    @Test
    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        super.saveAndLoad(new DBServiceImpl(connection));
    }

    @Test
    public void loadNotFound() throws SQLException, JPAException {
        super.loadNotFound(new DBServiceImpl(connection));
    }

    @Test
    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        super.insertAndUpdate(new DBServiceImpl(connection));
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
    }
}
