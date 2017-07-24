package ru.otus.db;

import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.jpa.JPAException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 10.07.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public class DBServiceTestCommon {
    protected static Connection connection;

    public static void createTable() throws SQLException {
        try (Connection createConnection = ConnectionHelper.getDefaultConnection()) {
            createConnection.createStatement().execute("CREATE TABLE IF NOT EXISTS db_example.users (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255), age INT(3) NOT NULL DEFAULT 0, PRIMARY KEY (id));");
            createConnection.commit();
        }
    }

    public DBService createDBService() {
        return null;
    }

    public void clear() throws SQLException {
        connection = ConnectionHelper.getDefaultConnection();
        connection.createStatement().execute("DELETE FROM users");
        connection.commit();
    }

    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        DBService dbService = createDBService();

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        UserDataSet loadedUser = dbService.load(1, UserDataSet.class);
        assert loadedUser.getId() == 1 && loadedUser.getName().equals("user1") && loadedUser.getAge() == 99;
    }

    public void loadNotFound() throws SQLException, JPAException {
        DBService dbService = createDBService();

        assert dbService.load(-1, UserDataSet.class) == null;
        // а вообще - в базе ничего не должно быть записано, так что не помешает проверить и следующее выражение:
        assert dbService.load(1, UserDataSet.class) == null;
    }

    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        DBService dbService = createDBService();

        UserDataSet user = new UserDataSet(1, "user2", 990);

        dbService.save(user);
        UserDataSet loadedUser = dbService.load(1, UserDataSet.class);
        assert loadedUser.getId() == 1 && loadedUser.getName().equals("user2") && loadedUser.getAge() == 990;

        user.setName("user3");
        user.setAge(490);

        dbService.save(user);
        loadedUser = dbService.load(1, UserDataSet.class);
        assert loadedUser.getId() == 1 && loadedUser.getName().equals("user3") && loadedUser.getAge() == 490;
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}
