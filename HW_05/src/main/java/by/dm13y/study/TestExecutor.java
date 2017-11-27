package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TestExecutor {

    private static void execTest(Object object, Method[] before, Method test, Method[] after){
        for (Method method : before) {
            ReflectionHelper.callMethod(object, method);
        }

        try {
            test.invoke(object);
        } catch (Throwable throwable) {
            if (!test.getAnnotation(Test.class).expected().equals(throwable.getCause().getClass())) {
                throw new RuntimeException(test.getName() + " is error -> ");
            }
        }
        for (Method method : after) {
            ReflectionHelper.callMethod(object, method);
        }
    }

    public static void exec(Class testClazz, Object... args) {
        Method[] before = ReflectionHelper.getMethodsByAnnotation(testClazz, Before.class);
        Method[] test = ReflectionHelper.getMethodsByAnnotation(testClazz, Test.class);
        Method[] after = ReflectionHelper.getMethodsByAnnotation(testClazz, After.class);

        if (test.length > 0) {
            Object instance = ReflectionHelper.getInstance(testClazz, args);

            for (Method method : test) {
                execTest(instance, before, method, after);
            }
        }
    }

    public static void exec(String packageName) {
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
        try {
            String[] classes = Files.walk(Paths.get(root.toURI()))
                    .filter(path -> path.toString().endsWith(".class"))
                    .map(path -> path.getFileName().toString().replace(".class", ""))
                    .toArray(String[]::new);

            for (String testClass : classes) {
                Class testClazz = Class.forName(packageName + "." + testClass);
                exec(testClazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execByReflection(String packageName) {
        List<ClassLoader> classLoaderList = new LinkedList<>();
        classLoaderList.add(ClasspathHelper.contextClassLoader());
        classLoaderList.add(ClasspathHelper.staticClassLoader());

        ClassLoader[] classLoaders = new ClassLoader[]{ClasspathHelper.contextClassLoader(),
                ClasspathHelper.staticClassLoader()};

        Scanner[] scanners = new Scanner[]{new SubTypesScanner(false), new ResourcesScanner()};

        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setScanners(scanners)
                .setUrls(ClasspathHelper.forClassLoader(classLoaders))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)));

        Reflections reflections = new Reflections(cb);
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        classes.forEach(clazz -> exec(clazz));
    }
}
