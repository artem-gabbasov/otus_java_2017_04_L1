package ru.otus.db.dbservices.impl;

import ru.otus.orm.datasets.NamedDataSet;
import ru.otus.db.ResultHandlerImpl;
import ru.otus.db.dbservices.DBServiceNamed;
import ru.otus.db.sql.ExecutorImpl;
import ru.otus.orm.jpa.JPAException;
import ru.otus.orm.jpa.JPAReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
public class DBServiceNamedImpl extends DBServiceImpl implements DBServiceNamed {
    public DBServiceNamedImpl(Connection connection) {
        super(connection);
    }

    public DBServiceNamedImpl(Supplier<Connection> connectionSupplier) {
        this(connectionSupplier.get());
    }

    @Override
    public <T extends NamedDataSet> T loadByName(String name, Class<T> clazz) throws SQLException, JPAException {
        String tableName = JPAReflectionHelper.getTableName(clazz);
        String nameColumnName = JPAReflectionHelper.getNameColumnName(clazz);
        return new ExecutorImpl().execQueryNamed(connection, tableName, nameColumnName, name, new ResultHandlerImpl<>(clazz));
    }
}
