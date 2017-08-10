package ru.otus.web.websockets;

import com.sun.scenario.effect.impl.prism.PrFilterContext;
import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.web.ServerContext;
import ru.otus.web.websockets.exceptions.AdminWebSocketException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

/**
 * Created by Artem Gabbasov on 03.08.2017.
 */
@WebSocket
public class AdminWebSocket {
    private static final Logger logger = Logger.getLogger(AdminWebSocket.class.getName());

    static {
        logger.setLevel(Level.FINE);
        try {
            Handler handler = new FileHandler("wsadmin.log");
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return DateFormatUtils.format(record.getMillis(), "dd.MM.yyyy hh:mm:ss") + " " + record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Session wsSession = null;

    private final HttpSession httpSession;
    private final DBServiceCached dbServiceCached;

    private final AdminWebSocketHandler adminWebSocketHandler;

    public AdminWebSocket(HttpSession httpSession, DBServiceCached dbServiceCached) {
        this.httpSession = httpSession;
        this.dbServiceCached = dbServiceCached;

        adminWebSocketHandler = new AdminWebSocketHandler(this, dbServiceCached);

        if (httpSession == null) {
            logger.fine("Http session is null");
        } else {
            logger.fine(httpSession.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws IOException {
        if (ServerContext.isAuthorized(httpSession)) {
            logger.fine("Message received : " + data);

            try {
                adminWebSocketHandler.handleJson(data);
            } catch (AdminWebSocketException e) {
                logger.severe(e.toString());
            }
        } else {
            reportForbiddenMessage();
            wsSession.disconnect();
        }
    }

    public void sendMessage(String json) throws IOException {
        if (ServerContext.isAuthorized(httpSession)) {
            try {
                logger.fine("Sending message: " + json);
                wsSession.getRemote().sendString(json);
            } catch (IOException e) {
                logger.severe(e.toString());
            }
        } else {
            reportForbiddenMessage();
            wsSession.disconnect();
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session wsSession) {
        logger.fine("Websocket opened (http session: " + httpSession.toString() + ")");
        setWSSession(wsSession);
    }

    public Session getWSSession() {
        return wsSession;
    }

    public void setWSSession(Session wsSession) {
        this.wsSession = wsSession;
    }

    /*
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    }*/

    private void reportForbiddenMessage() {

    }
}
