package ru.otus.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.jpa.JPAException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public class DBServiceTest {
    private static Connection connection;

    @Before
    public void clear() throws SQLException {
        connection = ConnectionHelper.getConnection(ConnectionHelperTest.url);
        connection.createStatement().execute("DELETE FROM users");
    }

    @Test
    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        DBService dbService = new DBServiceImpl(connection);
        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        UserDataSet loadedUser = dbService.load(1, UserDataSet.class);
        assert loadedUser.getId() == 1 && loadedUser.getName().equals("user1") && loadedUser.getAge() == 99;
    }

    @Test
    public void loadNotFound() throws SQLException, JPAException {
        assert new DBServiceImpl(connection).load(-1, UserDataSet.class) == null;
        // а вообще - в базе ничего не должно быть записано, так что не помешает проверить и следующее выражение:
        assert new DBServiceImpl(connection).load(1, UserDataSet.class) == null;
    }

    @Test
    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        DBService dbService = new DBServiceImpl(connection);
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

    @After
    public void rollback() throws SQLException {
        connection.rollback();
    }
}
