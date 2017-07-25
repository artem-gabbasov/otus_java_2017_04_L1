package ru.otus.db.dbservices;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.datasets.DataSet;
import ru.otus.datasets.UserDataSet;
import ru.otus.jpa.JPAException;

import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 12.07.2017.
 * <p>
 */
@SuppressWarnings({"EmptyMethod", "SameParameterValue", "WeakerAccess"})
public class DBServiceCachedTest extends DBServiceTestCommon {
    private DBServiceCacheEngine cacheEngine;

    @Override
    public DBService createDBService() {
        cacheEngine = new DBServiceCacheEngineImpl(1, 0, 0, true);
        return new DBServiceCachedImpl(connection, cacheEngine);
    }

    public DBService createDBServiceMaxElements(int maxElements) {
        cacheEngine = new DBServiceCacheEngineImpl(maxElements, 0, 0, true);
        return new DBServiceCachedImpl(connection, cacheEngine);
    }

    private DBService createDBServiceLifeTime(long lifeTimeMs) {
        cacheEngine = new DBServiceCacheEngineImpl(1, lifeTimeMs, 0, false);
        return new DBServiceCachedImpl(connection, cacheEngine);
    }

    private DBService createDBServiceIdleTime(long idleTimeMs) {
        cacheEngine = new DBServiceCacheEngineImpl(1, 0, idleTimeMs, false);
        return new DBServiceCachedImpl(connection, cacheEngine);
    }

    @BeforeClass
    public static void createTable() throws SQLException {
        DBServiceTestCommon.createTable();
    }

    @Before
    public void clear() throws SQLException {
        super.clear();
        cacheEngine = null;
    }

    @Test
    public void saveAndLoad() throws SQLException, IllegalAccessException, JPAException {
        super.saveAndLoad();
    }

    @Test
    public void loadNotFound() throws SQLException, JPAException {
        super.loadNotFound();
    }

    @Test
    public void insertAndUpdate() throws SQLException, IllegalAccessException, JPAException {
        super.insertAndUpdate();
    }

    @Test
    public void cache() throws IllegalAccessException, SQLException, JPAException {
        DBService dbService = createDBService();

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        dbService.load(1, UserDataSet.class);

        // при сохранении объект должен был попасть в кеш
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 0;

        cacheEngine.clear();
        dbService.load(1, UserDataSet.class);

        // после очистки кеша тот же самый объект должен был загрузиться уже из базы
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 1;

        dbService.load(1, UserDataSet.class);

        // после повторной загрузки объект должен снова попасть в кеш
        assert cacheEngine.getHitCount() == 2 && cacheEngine.getMissCount() == 1;
    }

    @Test
    public void cacheMaxElements() throws IllegalAccessException, SQLException, JPAException {
        DBService dbService = createDBService();

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        dbService.load(1, UserDataSet.class);

        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 0;

        DataSet user2 = new UserDataSet(2, "user2", 9);

        dbService.save(user2);
        dbService.load(2, UserDataSet.class);

        assert cacheEngine.getHitCount() == 2 && cacheEngine.getMissCount() == 0;

        dbService.load(1, UserDataSet.class);

        // первый юзер должен был уже "вытолкнуться" из кеша
        assert cacheEngine.getHitCount() == 2 && cacheEngine.getMissCount() == 1;
    }

    @Test
    public void cacheElementsCount() throws IllegalAccessException, SQLException, JPAException {
        DBService dbService = createDBServiceMaxElements(2);

        DataSet user = new UserDataSet(1, "user1", 99);
        dbService.save(user);

        assert cacheEngine.getElementsCount() == 1;

        DataSet user2 = new UserDataSet(2, "user2", 9);
        dbService.save(user2);

        assert cacheEngine.getElementsCount() == 2;

        cacheEngine.clear();

        assert cacheEngine.getElementsCount() == 0;
    }

    @Test
    // этот тест не совсем стабилен - он может иногда не выполниться, но для теста не совсем критично, можно перезапустить
    public void cacheLifeTime() throws IllegalAccessException, SQLException, JPAException, InterruptedException {
        DBService dbService = createDBServiceLifeTime(500);

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);
        Thread.sleep(100);
        dbService.load(1, UserDataSet.class);

        // lifeTime ещё не истёк
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 0;

        Thread.sleep(500);
        dbService.load(1, UserDataSet.class);

        // lifeTime уже должен был истечь
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 1;
    }

    @Test
    // этот тест не совсем стабилен - он может иногда не выполниться, но для теста не совсем критично, можно перезапустить
    public void cacheIdleTime() throws IllegalAccessException, SQLException, JPAException, InterruptedException {
        DBService dbService = createDBServiceIdleTime(500);

        DataSet user = new UserDataSet(1, "user1", 99);

        dbService.save(user);

        Thread.sleep(300);
        dbService.load(1, UserDataSet.class);

        // idleTime ещё не истёк
        assert cacheEngine.getHitCount() == 1 && cacheEngine.getMissCount() == 0;

        Thread.sleep(300);
        dbService.load(1, UserDataSet.class);

        // idleTime снова ещё не истёк, т.к. к нему обращались
        assert cacheEngine.getHitCount() == 2 && cacheEngine.getMissCount() == 0;

        Thread.sleep(600);
        dbService.load(1, UserDataSet.class);

        // idleTime уже должен был истечь
        assert cacheEngine.getHitCount() == 2 && cacheEngine.getMissCount() == 1;
    }

    @After
    public void rollback() throws SQLException {
        super.rollback();
        if (cacheEngine != null) {
            cacheEngine.dispose();
        }
    }
}
