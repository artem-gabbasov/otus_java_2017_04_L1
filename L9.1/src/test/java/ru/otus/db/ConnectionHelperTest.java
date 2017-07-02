package ru.otus.db;

import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 05.06.2017.
 * <p>
 */
public class ConnectionHelperTest {
    final static String url =
            "jdbc:mysql://" +       //db type
            "localhost:" +               //host name
            "3306/" +                    //port
            "db_example?" +              //db name
            "useSSL=false&" +            //do not use ssl
            "user=tully&" +              //login
            "password=tully";            //password

    @Test
    public void getConnection() throws SQLException {
        assert ConnectionHelper.getConnection(url).createStatement().execute("SELECT 'test'");
    }

    @Test
    public void getConnectionAutoCommit() throws SQLException {
        assert !ConnectionHelper.getConnection(url).getAutoCommit();
    }
}
