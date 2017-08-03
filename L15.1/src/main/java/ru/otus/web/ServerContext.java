package ru.otus.web;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

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

    private static boolean authorized = false;
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
