package ru.otus.web.websockets;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.db.dbservices.DBServiceCached;
import ru.otus.web.ServerContext;

/**
 * Created by Artem Gabbasov on 03.08.2017.
 */
class AdminWebSocketCreator implements WebSocketCreator {
    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new AdminWebSocket(servletUpgradeRequest.getHttpServletRequest().getSession(),
                ServerContext.getSpringBean("dbServiceCached", DBServiceCached.class));
    }
}
