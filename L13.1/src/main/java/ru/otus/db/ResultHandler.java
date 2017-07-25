package ru.otus.db;

import ru.otus.datasets.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 */
public interface ResultHandler<T extends DataSet> {
    T handle(ResultSet resultSet) throws SQLException;
}
