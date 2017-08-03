package ru.otus.db.dbservices;

import ru.otus.cache.CacheEngineImpl;
import ru.otus.datasets.DataSet;

/**
 * Created by Artem Gabbasov on 24.07.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public class DBServiceCacheEngineImpl extends CacheEngineImpl<DBServiceCacheKey, DataSet> implements DBServiceCacheEngine {
    public DBServiceCacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        super(maxElements, lifeTimeMs, idleTimeMs, isEternal);
    }
}
