package ru.otus.db.dbservices;

import ru.otus.datasets.NamedDataSet;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 */
public interface DBServiceNamed extends DBService {
    /**
     * Загружает именованный объект из БД
     * @param name                      имя объекта, который следует загрузить
     * @param clazz                     класс загружаемого объекта
     * @return                          загруженный объект, если загрузка прошла успешно. Иначе (в частности, если объект с заданным именем не найден), null
     * @throws SQLException             если возникают проблемы с БД
     * @throws JPAException             если класс объекта некорректно размечен аннотациями JPA
     */
    <T extends NamedDataSet> T loadByName(String name, Class<T> clazz) throws SQLException, JPAException;
}
