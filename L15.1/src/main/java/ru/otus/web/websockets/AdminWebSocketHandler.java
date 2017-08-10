package ru.otus.web.websockets;

import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.observable.Listener;
import ru.otus.orm.jpa.JPAException;
import ru.otus.web.CommunicationHelper;
import ru.otus.web.websockets.exceptions.AdminWebSocketClientException;
import ru.otus.web.websockets.exceptions.AdminWebSocketException;
import ru.otus.web.websockets.exceptions.AdminWebSocketServerException;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * Created by Artem Gabbasov on 06.08.2017.
 */
public class AdminWebSocketHandler implements Listener<Long> {
    private static final String JSON_MESSAGETYPE = "messageType";
    private static final String JSON_MESSAGETYPE_CLIENT_DBSERVICE = "dbService";
    private static final String JSON_MESSAGETYPE_CLIENT_ERROR = "error";
    private static final String JSON_MESSAGETYPE_SERVER_GETPARAMS = "getParams";

    private static final String JSON_DETAILS = "details";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_ACTION = "action";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_ACTION_SAVEUSER = "saveUser";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_ACTION_LOADUSER = "loadUser";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_ACTION_CLEARCACHE = "clearCache";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_PARAMS = "params";
    private static final String JSON_DETAILS_CLIENT_DBSERVICE_PARAMS_USERID = "userId";
    private static final String JSON_DETAILS_CLIENT_ERROR_ERRORMESSAGE = "errorMessage";
    private static final String JSON_DETAILS_SERVER_GETPARAMS_DATA = "data";
    private static final String JSON_DETAILS_SERVER_GETPARAMS_DATA_NAME = "name";
    private static final String JSON_DETAILS_SERVER_GETPARAMS_DATA_VALUE = "value";

    private final AdminWebSocket adminWebSocket;
    private final DBServiceCached dbServiceCached;

    public AdminWebSocketHandler(AdminWebSocket adminWebSocket, DBServiceCached dbServiceCached) {
        this.adminWebSocket = adminWebSocket;
        this.dbServiceCached = dbServiceCached;
    }

    public void handleJson(String json) throws AdminWebSocketException {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject object = reader.readObject();

        try {
            dispatchMessageType(object.getJsonString(JSON_MESSAGETYPE).getString(), object.getJsonObject(JSON_DETAILS));
        } catch (JPAException | SQLException | IllegalAccessException e) {
            AdminWebSocketException e2 = new AdminWebSocketException("Exception thrown while handling client JSON");
            e2.initCause(e);
            throw e2;
        }
    }

    private void dispatchMessageType(String messageType, JsonObject details) throws AdminWebSocketException, JPAException, SQLException, IllegalAccessException {
        switch (messageType) {
            case JSON_MESSAGETYPE_CLIENT_DBSERVICE:
                String action = details.getJsonString(JSON_DETAILS_CLIENT_DBSERVICE_ACTION).getString();
                switch (action) {
                    case JSON_DETAILS_CLIENT_DBSERVICE_ACTION_SAVEUSER:
                        CommunicationHelper.saveUser(dbServiceCached, getUserIdFromJson(details));
                        break;
                    case JSON_DETAILS_CLIENT_DBSERVICE_ACTION_LOADUSER:
                        CommunicationHelper.loadUser(dbServiceCached, getUserIdFromJson(details));
                        break;
                    case JSON_DETAILS_CLIENT_DBSERVICE_ACTION_CLEARCACHE:
                        CommunicationHelper.clearCache(dbServiceCached.getCacheEngine());
                        break;
                    default:
                        throw new AdminWebSocketServerException("Unrecognized dbServiceCached action: " + action);
                }
                break;
            case JSON_MESSAGETYPE_CLIENT_ERROR:
                throw new AdminWebSocketClientException(details.getJsonString(JSON_DETAILS_CLIENT_ERROR_ERRORMESSAGE).getString());
            default:
                throw new AdminWebSocketServerException("Unrecognized message type: " + messageType);
        }
    }

    private Long getUserIdFromJson(JsonObject details) {
        return details
            .getJsonObject(JSON_DETAILS_CLIENT_DBSERVICE_PARAMS)
            .getJsonNumber(JSON_DETAILS_CLIENT_DBSERVICE_PARAMS_USERID).longValue();
    }

    public static String prepareJson(String messageType, JsonObject details) {
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(Json.createObjectBuilder()
                .add(JSON_MESSAGETYPE, messageType)
                .add(JSON_DETAILS, details)
                .build()
            );
        }

        return stringWriter.toString();
    }

    @Override
    public void fireEvent(String variableName, Long value) {
        adminWebSocket.sendMessage(
            prepareJson(JSON_MESSAGETYPE_SERVER_GETPARAMS, Json.createObjectBuilder()
                .add(JSON_DETAILS_SERVER_GETPARAMS_DATA, Json.createArrayBuilder()
                    .add(Json.createObjectBuilder()
                        .add(JSON_DETAILS_SERVER_GETPARAMS_DATA_NAME, variableName)
                        .add(JSON_DETAILS_SERVER_GETPARAMS_DATA_VALUE, value)
                        .build())
                    .build())
                .build()
            )
        );
    }
}
