package ru.otus.db;

import ru.otus.cache.CacheEngine;
import ru.otus.datasets.DataSet;

/**
 * Created by Artem Gabbasov on 24.07.2017.
 * <p>
 */
public interface DBServiceCached extends DBService {
    DBServiceCacheEngine getCacheEngine();
}
