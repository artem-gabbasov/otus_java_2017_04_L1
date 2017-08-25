package ru.otus.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.db.*;
import ru.otus.db.dbservices.*;

import java.sql.Connection;

/**
 * Created by Artem Gabbasov on 22.07.2017.
 * <p>
 * Верхнеуровневый класс, содержащий в себе работу с сервером
 */
public class ServerManager implements AutoCloseable {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    static final String INDEX_PAGE = "index.html";
    static final String LOGIN_PAGE = "login";

    /**
     * Используются для общения между сервлетами
     */
    final static String AUTHORIZED_SESSIONS = "authorizedSessions";
    final static String REDIRECT_PAGE = "redirectPage";

    private final Server server;
    private Connection connection;

    public ServerManager() {
        this.server = new Server(PORT);
    }

    public void init() {
        connection = ConnectionHelper.getDefaultConnection();

        DBServiceCacheEngine cacheEngine = new DBServiceCacheEngineImpl(2, 0, 0, true);
        DBServiceCached dbServiceCached = new DBServiceCachedImpl(connection, cacheEngine);

        DBServiceNamed dbServiceNamed = new DBServiceNamedImpl(connection);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(dbServiceNamed)), "/login");
        context.addServlet(new ServletHolder(new AdminServlet(() -> dbServiceCached)), "/admin");

        server.setHandler(new HandlerList(resourceHandler, context));
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }

    @Override
    public void close()throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
