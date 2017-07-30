package ru.otus.web;

/**
 * Created by Artem Gabbasov on 22.07.2017.
 * <p>
 * Класс для связи сервлетов
 */
public class ServerContext {
    static final String INDEX_PAGE = "index.html";
    static final String LOGIN_PAGE = "login";

    static boolean authorized = false;
    static String redirectPage = "";

    public static boolean isAuthorized() {
        return authorized;
    }

    public static void setAuthorized(boolean authorized) {
        ServerContext.authorized = authorized;
    }

    public static String getRedirectPage() {
        return redirectPage.equals("") ? INDEX_PAGE : redirectPage;
    }

    public static void setRedirectPage(String redirectPage) {
        ServerContext.redirectPage = redirectPage;
    }
}
