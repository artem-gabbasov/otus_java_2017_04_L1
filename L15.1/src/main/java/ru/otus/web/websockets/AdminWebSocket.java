package ru.otus.web.websockets;

import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.web.ServerContext;
import ru.otus.web.websockets.exceptions.AdminWebSocketException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    /**
     * Websocket'ная сессия
     */
    private Session wsSession = null;

    /**
     * Нужна для проверки, авторизован ли пользователь
     */
    private final HttpSession httpSession;

    private final AdminWebSocketHandler adminWebSocketHandler;

    public AdminWebSocket(HttpSession httpSession, DBServiceCached dbServiceCached) {
        this.httpSession = httpSession;

        adminWebSocketHandler = new AdminWebSocketHandler(this, dbServiceCached);

        if (httpSession == null) {
            logger.fine("Http session is null");
        } else {
            logger.fine(httpSession.toString());
        }
    }

    @SuppressWarnings("unused")
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

    public void sendMessage(String json) {
        if (ServerContext.isAuthorized(httpSession)) {
            doSendMessage(json);
        } else {
            reportForbiddenMessage();

            try {
                wsSession.disconnect();
            } catch (IOException e) {
                logger.severe(e.toString());
            }
        }
    }

    private void doSendMessage(String json) {
        try {
            logger.fine("Sending message: " + json);
            if (wsSession.isOpen()) {
                wsSession.getRemote().sendString(json);
            }
        } catch (IOException e) {
            logger.severe(e.toString());
        }
    }

    @SuppressWarnings("unused")
    @OnWebSocketConnect
    public void onOpen(Session wsSession) {
        logger.fine("Websocket opened (http session: " + httpSession.toString() + ")");
        this.wsSession = wsSession;
    }

    /**
     * Сообщает клиенту, что пользователь больше не авторизован
     */
    private void reportForbiddenMessage() {
        doSendMessage(AdminWebSocketHandler.prepareMessageNotAuthorized());
    }
}
