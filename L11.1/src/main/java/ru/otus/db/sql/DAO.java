package ru.otus.db.sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 08.07.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public class DAO {
    public static String getQueryString(String tableName, String idColumnName) {
        return "SELECT * FROM " + tableName + " WHERE " + SQLCommons.escapeColumnName(idColumnName) + " = ?;";
    }

    public static String getUpdateString(String tableName, String[] columnNames, String idColumnName) {
        return "INSERT INTO " + tableName + " " + getColumnsSequence(columnNames) + " VALUES " + getValuesSequence(columnNames.length) + " ON DUPLICATE KEY UPDATE " + getColumnsSequenceForUpdate(columnNames, idColumnName) + ";";
    }

    /**
     * Подготавливает имена столбцов для использования в запросе
     * @param columnNames   имена столбцов
     * @return              строка с подготовленными для использования в запросе именами столбцов
     */
    public static String getColumnsSequence(String[] columnNames) {
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
    public static String getColumnsSequenceForUpdate(String[] columnNames, String idColumnName) {
        List<String> escapedList = Arrays.stream(columnNames)
                .filter(columnName -> !columnName.equals(idColumnName))
                .map(SQLCommons::escapeColumnName)
                .map(DAO::processColumnForUpdate)
                .collect(Collectors.toList());
        return SQLCommons.ofList(escapedList);
    }

    /**
     * Возвращает строку подстановочных символов для ввода в запрос значений
     * @param count количество значений, для которых требуются подстановочные символы
     * @return      строка с необходимым количеством подстановочных символов
     */
    public static String getValuesSequence(int count) {
        return '(' + SQLCommons.ofList(Collections.nCopies(count, "?")) + ')';
    }

    /**
     * Подготавливает имя отдельного столбца для использования в части ON DUPLICATE KEY UPDATE
     * @param columnName    имя столбца
     * @return              строка с подготовленным для использования в части ON DUPLICATE KEY UPDATE именем столбца
     */
    public static String processColumnForUpdate(String columnName) { return columnName + "=VALUES(" + columnName + ")"; }
}
