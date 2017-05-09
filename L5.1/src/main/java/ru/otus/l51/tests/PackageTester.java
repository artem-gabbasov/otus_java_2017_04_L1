package ru.otus.l51.tests;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.*;

/**
 * Created by Artem Gabbasov on 09.05.2017.
 */
public class PackageTester {
    private final List<Class<?>> classes;

    public PackageTester(String packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage(packageName))
                // поскольку нас интересуют наследники класса Object,
                // нам здесь надо явно указать, что Object исключать из поиска не следует
            .setScanners(new SubTypesScanner(false))
            .filterInputsBy(new FilterBuilder().includePackage(packageName))
        );

        classes = new ArrayList<>();
        classes.addAll(reflections.getSubTypesOf(Object.class));
    }

    public void launch() {
        new ClassListTester(classes).launch();
    }
}
