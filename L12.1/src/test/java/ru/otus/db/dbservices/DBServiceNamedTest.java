package ru.otus.db.dbservices;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.datasets.NamedDataSet;
import ru.otus.datasets.NamedTestDataSet;
import ru.otus.db.ConnectionHelper;
import ru.otus.jpa.JPAException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
@SuppressWarnings({"WeakerAccess", "EmptyMethod"})
public class DBServiceNamedTest extends DBServiceTestCommon {
    public DBServiceNamed createDBServiceNamed() {
        return new DBServiceNamedImpl(connection);
    }

    @BeforeClass
    public static void createTable() throws SQLException {
        try (Connection createConnection = ConnectionHelper.getDefaultConnection()) {
            createConnection.createStatement().execute("CREATE TABLE IF NOT EXISTS db_example.namedTest (id BIGINT(20) NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL, passwordMD5 VARCHAR(255), PRIMARY KEY (id));");
            createConnection.commit();
        }
    }

    @Override
    @Before
    public void clear() throws SQLException {
        connection = ConnectionHelper.getDefaultConnection();
        connection.createStatement().execute("DELETE FROM namedTest");
        connection.commit();
    }

    @Test
    public void loadByName() throws SQLException, IllegalAccessException, JPAException {
        DBServiceNamed dbService = createDBServiceNamed();

        NamedDataSet namedDataSet = new NamedTestDataSet(1, "user1", "asdasdasd");

        dbService.save(namedDataSet);
        NamedTestDataSet loadedDataSet = dbService.loadByName("user1", NamedTestDataSet.class);
        assert loadedDataSet.getId() == 1 && loadedDataSet.getName().equals("user1") && loadedDataSet.getPasswordMD5().equals("asdasdasd");
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
    }
}