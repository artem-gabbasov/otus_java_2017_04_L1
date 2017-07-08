package ru.otus.db;

import ru.otus.anytype.UnsupportedTypeException;
import ru.otus.anytype.ValueException;
import ru.otus.anytype.ValueGetHelper;
import ru.otus.anytype.getters.GeneralValueGetter;
import ru.otus.datasets.DataSet;
import ru.otus.jpa.JPAReflectionHelper;
import ru.otus.jpa.NoJPAAnnotationException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 08.06.2017.
 * <p>
 */
public class ResultHandlerImpl<T extends DataSet> implements ResultHandler<T> {
    private final Class<T> clazz;

    public ResultHandlerImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handle(ResultSet resultSet) throws SQLException {
        T result = null;

        if (resultSet.next()) {
            try {
                result = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                Map<Field, String> fieldColumnsMap = JPAReflectionHelper.getFieldColumnsMap(clazz);

                ValueGetHelper helper = new ValueGetHelper();
                for (Map.Entry<Field, String> entry : fieldColumnsMap.entrySet()) {
                    Field field = entry.getKey();
                    String columnName = entry.getValue();

                    GeneralValueGetter valueGetter = new ResultSetValueGetter(resultSet, columnName);

                    JPAReflectionHelper.setFieldValue(field, result, helper.accept(valueGetter, field.getType()));
                }
            } catch (IllegalAccessException | NoJPAAnnotationException | UnsupportedTypeException e) {
                e.printStackTrace();
            } catch (ValueException e) {
                if (e.getCause() instanceof SQLException) {
                    throw (SQLException)e.getCause();
                } else {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
