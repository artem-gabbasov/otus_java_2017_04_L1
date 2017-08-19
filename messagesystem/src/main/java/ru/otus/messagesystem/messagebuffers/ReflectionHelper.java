package ru.otus.messagesystem.messagebuffers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 19.08.2017.
 */
public class ReflectionHelper {
    public static Set<Class<?>> listSubInterfaces(Class<?> targetClass, Class<?> superInterface) {
        Set<Class<?>> interfaces = new HashSet<>();
        Class<?> clazz = targetClass;
        do {
            interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        } while (clazz != null);

        interfaces = interfaces.stream()
                .filter(superInterface::isAssignableFrom)
                .collect(Collectors.toSet());

        interfaces.remove(superInterface);
        return interfaces;
    }
}
