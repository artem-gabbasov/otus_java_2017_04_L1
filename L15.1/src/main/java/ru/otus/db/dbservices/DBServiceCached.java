package ru.otus.db.dbservices;

/**
 * Created by Artem Gabbasov on 24.07.2017.
 * <p>
 */
public interface DBServiceCached extends DBService {
    DBServiceCacheEngine getCacheEngine();
}
