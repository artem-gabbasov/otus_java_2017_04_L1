package ru.otus.l51.tests;

import ru.otus.l51.annotations.testing.After;
import ru.otus.l51.annotations.testing.Before;
import ru.otus.l51.annotations.testing.Test;
import ru.otus.l51.tests.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 05.05.2017.
 *
 * Класс для тестирования <b>одного</b> заданного класса.
 * Тестирование запускается методом {@code launch()}
 */
public class ClassTester {
    private final Object instance;
    private List<String> befores, tests, afters;

    public ClassTester(Class<?> clazz) {
        instance = ReflectionHelper.instantiate(clazz);
    }

    // находит и собирает в лист все методы класса с заданной аннотацией
    private List<String> collectMethodsByAnnotation(Class<? extends Annotation> annotation) {
        return Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotation))
                .map(Method::getName)
                .collect(Collectors.toList());
    }

    // находит и собирает в листы все методы класса с нужными нам аннотациями
    private void collectMethods() {
        befores = collectMethodsByAnnotation(Before.class);
        tests = collectMethodsByAnnotation(Test.class);
        afters = collectMethodsByAnnotation(After.class);
    }

    // вызывает заданный тестовый метод
    private void runTest(String methodName) {
        if (befores != null) runAdditionalMethods(befores);
        ReflectionHelper.callMethod(instance, methodName);
        if (afters != null) runAdditionalMethods(afters);
    }

    // вызывает методы, имена которых перечислены в листе
    private void runAdditionalMethods(List<String> list) {
        list.stream()
               .forEach((methodName) -> ReflectionHelper.callMethod(instance, methodName));
    }

    // запускает тесты, как нам нужно, для заданного класса
    public void launch() {
        collectMethods();
        tests.stream()
                .forEach(this::runTest);
    }
}
