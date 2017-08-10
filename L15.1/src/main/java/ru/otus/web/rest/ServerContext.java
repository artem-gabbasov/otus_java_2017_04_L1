package ru.otus.web.rest;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Set<HttpSession> authorizedSessions = Collections.newSetFromMap(new ConcurrentHashMap<HttpSession, Boolean>());

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

    /**
     * Убирает <code>urlPattern</code> из строки <code>uri</code>, чтобы получить адрес страницы для перенаправления
     * @param uri           исходная ссылка, содержащая адрес перенаправления
     * @param urlPattern    паттерн, задающий сервлет для обработки запроса
     * @return              адрес страницы для перенаправления
     */
    public static String getRedirectPath(String uri, String urlPattern) {
        return uri.replaceFirst(urlPattern, "");
    }
}
