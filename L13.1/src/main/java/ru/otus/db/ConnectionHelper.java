package ru.otus.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 05.06.2017.
 * <p>
 * Класс для установления соединения с БД
 */
public class ConnectionHelper {
    private final static String defaultUrl =
            "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //port
                    "db_example?" +              //db name
                    "useSSL=false&" +            //do not use ssl
                    "user=tully&" +              //login
                    "password=tully";            //password

    @SuppressWarnings("WeakerAccess")
    public static Connection getConnection(@SuppressWarnings("SameParameterValue") String url) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            Connection connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getDefaultConnection() {
        return getConnection(defaultUrl);
    }
}
