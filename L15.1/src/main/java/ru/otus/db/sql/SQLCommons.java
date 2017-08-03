package ru.otus.db.sql;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by Artem Gabbasov on 02.07.2017.
 * <p>
 * Вспомогательный класс для работы с SQL-запросами
 */
class SQLCommons {
    /**
     * "Оборачивает" ("окавычивает") имя столбца, чтобы не было коллизий
     * @param columnName    имя столбца
     * @return              окавыченное имя столбца
     */
    static String escapeColumnName(String columnName) {
        return '`' + columnName + '`';
    }

    /**
     * Перечисляет значения из списка для использования в запросах
     * @param list  список значений, которые необходимо перечислить
     * @return      объединённая строка для использования в запросах
     */
    static String ofList(List<String> list) {
        return StringUtils.join(list, ',');
    }
}
