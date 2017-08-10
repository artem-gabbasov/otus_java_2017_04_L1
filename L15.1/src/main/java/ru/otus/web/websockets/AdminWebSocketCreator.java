package ru.otus.web.websockets;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by Artem Gabbasov on 03.08.2017.
 */
public class AdminWebSocketCreator implements WebSocketCreator {
    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new AdminWebSocket(servletUpgradeRequest.getHttpServletRequest().getSession(), null);
    }
}
