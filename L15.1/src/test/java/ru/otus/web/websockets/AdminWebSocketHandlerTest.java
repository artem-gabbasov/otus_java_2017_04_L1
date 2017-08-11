package ru.otus.web.websockets;

import org.junit.Test;
import ru.otus.db.connections.ConnectionHelper;
import ru.otus.db.dbservices.DBServiceCacheEngine;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.db.dbservices.DBServiceCachedTest;
import ru.otus.db.dbservices.impl.DBServiceCacheEngineImpl;
import ru.otus.db.dbservices.impl.DBServiceCachedImpl;
import ru.otus.orm.datasets.instances.UserDataSet;
import ru.otus.orm.jpa.JPAException;
import ru.otus.web.websockets.exceptions.AdminWebSocketException;

import javax.json.Json;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class AdminWebSocketHandlerTest {
    @Test
    public void prepareJson() {
        assert AdminWebSocketHandler.prepareJson("getParams", Json.createObjectBuilder()
            .add("data", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                    .add("name", "maxElements")
                    .add("value", 5)
                    .build())
                .add(Json.createObjectBuilder()
                    .add("name", "hitCount")
                    .add("value", 55)
                    .build())
                .build())
            .build()
        ).equals(
            "{" +
                "\"messageType\":\"getParams\"," +
                "\"details\":{" +
                    "\"data\":[" +
                        "{" +
                            "\"name\":\"maxElements\"," +
                            "\"value\":5" +
                        "}," +
                        "{" +
                            "\"name\":\"hitCount\"," +
                            "\"value\":55" +
                        "}" +
                    "]" +
                "}" +
            "}"
        );
    }

    @Test
    public void prepareMessageNotAuthorized() {
        assert AdminWebSocketHandler.prepareMessageNotAuthorized().equals(
                "{" +
                        "\"messageType\":\"notAuthorized\"," +
                        "\"details\":{}" +
                "}"
        );
    }

    @Test
    public void handleJson() throws AdminWebSocketException, JPAException, SQLException {
        DBServiceCacheEngine cacheEngine = new DBServiceCacheEngineImpl(1, 0, 0, true);
        DBServiceCached dbServiceCached = new DBServiceCachedImpl(ConnectionHelper.getDefaultConnection(), cacheEngine);

        AdminWebSocketHandler handler = new AdminWebSocketHandler(null, dbServiceCached);

        // подписка в этом тесте нам только мешает
        dbServiceCached.getCacheEngine().removeListener(handler);

        handler.handleJson(
            "{" +
                "\"messageType\":\"dbService\"," +
                "\"details\":{" +
                    "\"action\":\"saveUser\"," +
                    "\"params\":{" +
                        "\"userId\":\"5\"" +
                    "}" +
                "}" +
            "}"
        );

        assert cacheEngine.getElementsCount() == 1
                && cacheEngine.getHitCount() == 0
                && cacheEngine.getMissCount() == 0;

        UserDataSet user = dbServiceCached.load(5, UserDataSet.class);

        assert cacheEngine.getElementsCount() == 1
                && user != null
                && cacheEngine.getHitCount() == 1
                && cacheEngine.getMissCount() == 0;
    }
}