package ru.otus.db.sql;

import ru.otus.anytype.setters.GeneralValueSetter;
import ru.otus.anytype.ValueSetHelper;
import ru.otus.db.PreparedStatementValueSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 07.06.2017.
 * <p>
 */
public class SQLUpdateHelperImpl implements SQLUpdateHelper {
    @Override
    public void execUpdate(Connection connection, String tableName, Map<String, Object> fieldMap, String idColumnName) throws SQLException {
        int arraySize = fieldMap.size();

        String[] columnNames = new String[arraySize];
        Object[] fieldValues = new Object[arraySize];

        int arrayIndex = 0;
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            columnNames[arrayIndex] = entry.getKey();
            fieldValues[arrayIndex] = entry.getValue();
            arrayIndex++;
        }

        try (PreparedStatement stmt = connection.prepareStatement(getUpdateString(tableName, columnNames, idColumnName))) {
            prepareUpdate(stmt, fieldValues);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw e1;
            }
            throw e;
        }
    }

    public String getUpdateString(String tableName, String[] columnNames, String idColumnName) {
        return "INSERT INTO " + tableName + " " + getColumnsSequence(columnNames) + " VALUES " + getValuesSequence(columnNames.length) + " ON DUPLICATE KEY UPDATE " + getColumnsSequenceForUpdate(columnNames, idColumnName) + ";";
    }

    /**
     * Подготавливает имена столбцов для использования в запросе
     * @param columnNames   имена столбцов
     * @return              строка с подготовленными для использования в запросе именами столбцов
     */
    public String getColumnsSequence(String[] columnNames) {
        List<String> escapedList = Arrays.stream(columnNames)
                .map(SQLCommons::escapeColumnName)
                .collect(Collectors.toList());
        return '(' + SQLCommons.ofList(escapedList) + ')';
    }

    /**
     * Подготавливает имена столбцов для использования в части ON DUPLICATE KEY UPDATE
     * @param columnNames   имена столбцов
     * @param idColumnName  имя столбца идентификатора (в результирующую строку этот столбец не попадает)
     * @return              строка с подготовленными для использования в части ON DUPLICATE KEY UPDATE именами столбцов
     */
    public String getColumnsSequenceForUpdate(String[] columnNames, String idColumnName) {
        List<String> escapedList = Arrays.stream(columnNames)
                .filter(columnName -> !columnName.equals(idColumnName))
                .map(SQLCommons::escapeColumnName)
                .map(this::processColumnForUpdate)
                .collect(Collectors.toList());
        return SQLCommons.ofList(escapedList);
    }

    /**
     * Возвращает строку подстановочных символов для ввода в запрос значений
     * @param count количество значений, для которых требуются подстановочные символы
     * @return      строка с необходимым количеством подстановочных символов
     */
    public String getValuesSequence(int count) {
        return '(' + SQLCommons.ofList(Collections.nCopies(count, "?")) + ')';
    }

    /**
     * Подготавливает имя отдельного столбца для использования в части ON DUPLICATE KEY UPDATE
     * @param columnName    имя столбца
     * @return              строка с подготовленным для использования в части ON DUPLICATE KEY UPDATE именем столбца
     */
    @SuppressWarnings("WeakerAccess")
    public String processColumnForUpdate(String columnName) { return columnName + "=VALUES(" + columnName + ")"; }

    /**
     * Задаёт значения параметров для запроса
     * @param stmt      объект запроса
     * @param values    массив параметров, которые следует задать
     */
    @SuppressWarnings("WeakerAccess")
    public void prepareUpdate(PreparedStatement stmt, Object[] values) {
        GeneralValueSetter valueSetter = new PreparedStatementValueSetter(stmt);
        ValueSetHelper helper = new ValueSetHelper();
        try {
            for(Object value : values) {
                helper.accept(valueSetter, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
