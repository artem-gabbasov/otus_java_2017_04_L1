package ru.otus.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 05.06.2017.
 * <p>
 * Класс для установления соединения с БД
 */
class ConnectionHelper {
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
}
