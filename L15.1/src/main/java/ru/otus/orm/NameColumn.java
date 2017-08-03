package ru.otus.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Artem Gabbasov on 25.07.2017.
 * <p>
 * Аннотация, указывающая, что колонка задаёт имя записи
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameColumn {
}
