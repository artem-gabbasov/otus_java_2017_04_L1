package ru.otus.db.dbservices.impl;

import ru.otus.cache.impl.CacheEngineImpl;
import ru.otus.orm.datasets.DataSet;
import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCacheKey;

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
