package ru.otus.web.rest;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Artem Gabbasov on 22.07.2017.
 * <p>
 * Класс для связи сервлетов
 */
@SuppressWarnings("SameParameterValue")
class ServerContext {
    static final String INDEX_PAGE = "index.html";
    static final String LOGIN_PAGE = "login";

    private static final String SPRING_BEANS_SPECIFICATION = "SpringBeans.xml";

    private static final Set<HttpSession> authorizedSessions = new HashSet<>();
    private static String redirectPage = "";

    private final static GenericApplicationContext context;

    static {
        context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions(SPRING_BEANS_SPECIFICATION);
        context.refresh();
    }

    /**
     * Возвращает bean, инициализированный через Spring
     * @param beanClassName имя класса, объект которого следует инициализировать
     * @param beanClass     класс, объект которого следует инициализировать
     * @param <T>           возвращаемый класс (объект которого следует инициализировать)
     * @return              инициализированный через Spring объект, который можно передавать в сервлет
     */
    public static <T> T getSpringBean(String beanClassName, Class<T> beanClass) {
        return context.getBeanFactory().getBean(beanClassName, beanClass);
    }

    public static boolean isAuthorized(HttpSession session) {
        return authorizedSessions.contains(session);
    }

    public static void setAuthorized(HttpSession session, boolean authorized) {
        if (authorized) {
            authorizedSessions.add(session);
        } else {
            authorizedSessions.remove(session);
        }
    }

    public static String getRedirectPage() {
        return redirectPage.equals("") ? INDEX_PAGE : redirectPage;
    }

    public static void setRedirectPage(String redirectPage) {
        ServerContext.redirectPage = redirectPage;
    }
}
