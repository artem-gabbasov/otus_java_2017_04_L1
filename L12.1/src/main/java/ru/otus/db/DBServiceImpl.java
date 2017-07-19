package ru.otus.db;

import com.sun.istack.internal.Nullable;
import ru.otus.datasets.DataSet;
import ru.otus.db.sql.ExecutorImpl;
import ru.otus.jpa.JPAException;
import ru.otus.jpa.JPAReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) throws SQLException, IllegalAccessException, JPAException {
        String tableName = JPAReflectionHelper.getTableName(dataSet.getClass());
        Map<String, Object> fieldMap = JPAReflectionHelper.getColumnValuesMap(dataSet, dataSet.getClass());
        String idColumnName = JPAReflectionHelper.getIdColumnName(dataSet.getClass());
        new ExecutorImpl().execUpdate(connection, tableName, fieldMap, idColumnName);
    }

    @Override
    @Nullable public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, JPAException {
        String tableName = JPAReflectionHelper.getTableName(clazz);
        String idColumnName = JPAReflectionHelper.getIdColumnName(clazz);
        return new ExecutorImpl().execQuery(connection, tableName, idColumnName, id, new ResultHandlerImpl<>(clazz));
    }
}
