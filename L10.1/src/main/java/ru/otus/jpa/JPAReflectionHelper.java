package ru.otus.jpa;

import ru.otus.datasets.DataSet;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 * Класс, созданный для упрощения работы с аннотациями JPA
 */
public class JPAReflectionHelper {
    /**
     * Возвращает имя таблицы, связанной с классом
     * @param clazz                         класс, связанный с таблицей
     * @return                              имя соответствующей таблицы
     * @throws NoJPAAnnotationException     если класс не размечен аннотацией @Table
     * @throws IllegalJPAStateException     если имя таблицы пустое
     */
    public static String getTableName(Class<? extends DataSet> clazz) throws NoJPAAnnotationException, IllegalJPAStateException {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new NoJPAAnnotationException(Table.class, clazz);
        }

        String tableName = table.name();
        if (tableName.equals("")) throw new IllegalJPAStateException("Empty table name in " + clazz);

        return table.schema().equals("") ? tableName : table.schema() + '.' + tableName;
    }

    private static Object getFieldValue(Field field, DataSet object) throws IllegalAccessException {
        boolean isAccessible = true;
        try {
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } finally {
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    public static void setFieldValue(Field field, DataSet object, Object value) throws IllegalAccessException {
        boolean isAccessible = true;
        try {
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } finally {
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * Возвращает имя столбца идентификатора
     * @param clazz                         класс, для которого определяется имя столбца
     * @return                              имя столбца идентификатора
     * @throws NoJPAAnnotationException     если ни одно поле не размечено аннотацией @Id
     * @throws IllegalJPAStateException     если несколько полей размечены аннотацией @Id
     */
    public static String getIdColumnName(Class<? extends DataSet> clazz) throws NoJPAAnnotationException, IllegalJPAStateException {
        List<Field> idFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());

        if (idFields.size() == 0) {
            throw new NoJPAAnnotationException(Id.class, clazz);
        }

        if (idFields.size() > 1) {
            throw new IllegalJPAStateException("Multiple @Id columns");
        }

        return idFields.get(0).getName();
    }

    /**
     * Возвращает значения полей объекта, доступные по названиям столбцов соответствующей таблицы в БД
     * @param object                        объект, значения полей которого необходимо получить
     * @param clazz                         класс объекта
     * @return                              отображение из имён столбцов таблицы (БД) в их значения для переданного объекта
     * @throws NoJPAAnnotationException     если ни одно поле не размечено аннотацией @Column
     * @throws IllegalAccessException       если возникают проблемы с доступом к полям
     */
    public static Map<String, Object> getColumnValuesMap(DataSet object, Class<? extends DataSet> clazz) throws NoJPAAnnotationException, IllegalAccessException {
        Map<String, Object> result = new HashMap<>();

        Map<Field, String> fieldColumnsMap = getFieldColumnsMap(clazz);

        for (Map.Entry<Field, String> entry : fieldColumnsMap.entrySet()) {
            result.put(entry.getValue(), getFieldValue(entry.getKey(), object));
        }

        return result;
    }

    /**
     * Возвращает имена столбцов соответствующей классу таблицы БД, доступные по полям класса
     * @param clazz                         класс, для которого определяется соответствие
     * @return                              отображение из полей класса в имена соответствующих столбцов таблицы (БД)
     * @throws NoJPAAnnotationException     если ни одно поле не размечено аннотацией @Column
     */
    public static Map<Field, String> getFieldColumnsMap(Class<? extends DataSet> clazz) throws NoJPAAnnotationException {
        Map<Field, String> result = new HashMap<>();

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        String name = column.name().equals("") ? field.getName() : column.name();
                        result.put(field, name);
                    }
                });

        if (result.size() == 0) {
            throw new NoJPAAnnotationException(Column.class, clazz);
        }

        return result;
    }
}
