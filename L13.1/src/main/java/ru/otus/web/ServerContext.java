package ru.otus.web;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by Artem Gabbasov on 22.07.2017.
 * <p>
 * Класс для связи сервлетов
 */
public class ServerContext {
    static final String INDEX_PAGE = "index.html";
    static final String LOGIN_PAGE = "login";

    static final String SPRING_BEANS_SPECIFICATION = "SpringBeans.xml";

    static boolean authorized = false;
    static String redirectPage = "";

    /**
     * Возвращает bean, инициализированный через Spring
     * @param beanClassName имя класса, объект которого следует инициализировать
     * @param beanClass     класс, объект которого следует инициализировать
     * @param <T>           возвращаемый класс (объект которого следует инициализировать)
     * @return              инициализированный через Spring объект, который можно передавать в сервлет
     */
    public static <T> T getSpringBean(String beanClassName, Class<T> beanClass) {
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions(SPRING_BEANS_SPECIFICATION);
        context.refresh();

        return context.getBeanFactory().getBean(beanClassName, beanClass);
    }

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
