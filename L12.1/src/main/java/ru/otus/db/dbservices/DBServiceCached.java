package ru.otus.db.dbservices;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 24.07.2017.
 * <p>
 */
public interface DBServiceCached extends DBService {
    String STATISTICS_MAXELEMENTS = "maxElements";
    String STATISTICS_ELEMENTSCOUNT = "elementsCount";
    String STATISTICS_LIFETIME = "lifeTime";
    String STATISTICS_IDLETIME = "idleTime";
    String STATISTICS_HITCOUNT = "hitCount";
    String STATISTICS_MISSCOUNT = "missCount";

    DBServiceCacheEngine getCacheEngine();

    @Override
    default Map<String, Object> getStatistics() {
        DBServiceCacheEngine cacheEngine = getCacheEngine();

        if (cacheEngine == null) {
            return DBService.super.getStatistics();
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put(STATISTICS_MAXELEMENTS, cacheEngine.getMaxElements());
        statistics.put(STATISTICS_ELEMENTSCOUNT, cacheEngine.getElementsCount());
        statistics.put(STATISTICS_LIFETIME, cacheEngine.getLifeTimeMs());
        statistics.put(STATISTICS_IDLETIME, cacheEngine.getIdleTimeMs());
        statistics.put(STATISTICS_HITCOUNT, cacheEngine.getHitCount());
        statistics.put(STATISTICS_MISSCOUNT, cacheEngine.getMissCount());

        return statistics;
    }
}
