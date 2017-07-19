package ru.otus.jpa;

import java.lang.annotation.Annotation;

/**
 * Created by Artem Gabbasov on 01.07.2017.
 * <p>
 */
public class NoJPAAnnotationException extends JPAException {
    public NoJPAAnnotationException(Class<? extends Annotation> annotationClass, Class<?> targetClass) {
        super("Annotation @" + annotationClass.getSimpleName() + " not found in " + targetClass.getName());
    }
}
