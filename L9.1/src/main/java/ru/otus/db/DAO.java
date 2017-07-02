package ru.otus.db;

import com.sun.istack.internal.Nullable;
import ru.otus.datasets.DataSet;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.06.2017.
 * <p>
 * Интерфейс взаимодействия DataSet'ов с БД (сохранения и загрузки)
 */
@SuppressWarnings("WeakerAccess")
public interface DAO {
    /**
     * Сохраняет объект в БД
     * @param dataSet                   объект для сохранения
     * @throws SQLException             если возникают проблемы с БД
     * @throws IllegalAccessException   если возникают проблемы с доступом к полям объекта
     * @throws JPAException             если класс объекта некорректно размечен аннотациями JPA
     */
    <T extends DataSet> void save(T dataSet) throws SQLException, IllegalAccessException, JPAException;

    /**
     * Загружает объект из БД
     * @param id                        идентификатор объекта, который следует загрузить
     * @param clazz                     класс загружаемого объекта
     * @return                          загруженный объект, если загрузка прошла успешно. Иначе (в частности, если объект с заданным идентификатором не найден), null
     * @throws SQLException             если возникают проблемы с БД
     * @throws JPAException             если класс объекта некорректно размечен аннотациями JPA
     */
    @Nullable
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException, JPAException;
}
