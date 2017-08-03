package ru.otus.db.connections;

import java.sql.Connection;
import java.util.function.Supplier;

/**
 * Created by Artem Gabbasov on 30.07.2017.
 * <p>
 */
public class DefaultDBConnectionSupplier implements Supplier<Connection> {
    private final Connection connection;

    public DefaultDBConnectionSupplier() {
        connection = ConnectionHelper.getDefaultConnection();
    }

    @Override
    public Connection get() {
        return connection;
    }
}
