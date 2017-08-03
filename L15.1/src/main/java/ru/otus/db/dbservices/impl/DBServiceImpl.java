package ru.otus.db.dbservices.impl;

import ru.otus.orm.datasets.DataSet;
import ru.otus.db.ResultHandlerImpl;
import ru.otus.db.dbservices.DBService;
import ru.otus.db.sql.ExecutorImpl;
import ru.otus.orm.jpa.JPAException;
import ru.otus.orm.jpa.JPAReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public class DBServiceImpl implements DBService {
    @SuppressWarnings("WeakerAccess")
    protected final Connection connection;

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
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, JPAException {
        String tableName = JPAReflectionHelper.getTableName(clazz);
        String idColumnName = JPAReflectionHelper.getIdColumnName(clazz);
        return new ExecutorImpl().execQuery(connection, tableName, idColumnName, id, new ResultHandlerImpl<>(clazz));
    }
}
