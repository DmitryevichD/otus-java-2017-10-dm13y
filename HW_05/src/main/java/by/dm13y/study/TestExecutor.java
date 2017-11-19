package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.*;

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

        Object instance = ReflectionHelper.getInstance(testClazz, args);

        for (Method method : test) {
            execTest(instance, before, method, after);
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

}
