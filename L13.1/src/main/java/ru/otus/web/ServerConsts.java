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
 * Константы для работы сервлетов
 */
public class ServerConsts {
    static final String INDEX_PAGE = "index.html";
    static final String LOGIN_PAGE = "login";

    /**
     * Используются для общения между сервлетами
     */
    final static String AUTHORIZED_FLAG = "authorized";
    final static String REDIRECT_PAGE = "redirectPage";
}
