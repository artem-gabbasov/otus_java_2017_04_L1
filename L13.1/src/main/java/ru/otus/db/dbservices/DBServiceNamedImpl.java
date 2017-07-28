package ru.otus.db.dbservices;

import ru.otus.datasets.NamedDataSet;
import ru.otus.db.ResultHandlerImpl;
import ru.otus.db.sql.ExecutorImpl;
import ru.otus.jpa.JPAException;
import ru.otus.jpa.JPAReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
public class DBServiceNamedImpl extends DBServiceImpl implements DBServiceNamed {
    public DBServiceNamedImpl(Connection connection) {
        super(connection);
    }

    @Override
    public <T extends NamedDataSet> T loadByName(String name, Class<T> clazz) throws SQLException, JPAException {
        String tableName = JPAReflectionHelper.getTableName(clazz);
        String nameColumnName = JPAReflectionHelper.getNameColumnName(clazz);
        return new ExecutorImpl().execQueryNamed(connection, tableName, nameColumnName, name, new ResultHandlerImpl<>(clazz));
    }
}
