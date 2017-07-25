package ru.otus.db.dbservices;

import ru.otus.datasets.DataSet;

import java.util.Objects;

/**
 * Created by Artem Gabbasov on 15.07.2017.
 * <p>
 * Класс, используемый в качестве ключа кеша для нашей задачи с хранением DataSet'ов
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DBServiceCacheKey {
    private long id;
    private Class<? extends DataSet> clazz;

    public DBServiceCacheKey(long id, Class<? extends DataSet> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends DataSet> clazz) {
        this.clazz = clazz;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clazz);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        DBServiceCacheKey other = (DBServiceCacheKey)obj;
        return other.id == getId() && other.clazz.equals(getClazz());
    }
}
