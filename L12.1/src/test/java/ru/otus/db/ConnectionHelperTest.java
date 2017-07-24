package ru.otus.db;

import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 05.06.2017.
 * <p>
 */
public class ConnectionHelperTest {
    @Test
    public void getConnection() throws SQLException {
        assert ConnectionHelper.getDefaultConnection().createStatement().execute("SELECT 'test'");
    }

    @Test
    public void getConnectionAutoCommit() throws SQLException {
        assert !ConnectionHelper.getDefaultConnection().getAutoCommit();
    }
}
