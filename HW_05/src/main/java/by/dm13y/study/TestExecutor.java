package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

public class TestExecutor {

    private static void execTest(Object object, Method[] before, Method test, Method[] after) throws Throwable{
        for (Method method : before) {
            ReflectionHelper.callMethod(object, method);
        }

        try {
            test.invoke(object);
        } catch (Throwable throwable) {
            if (!test.getAnnotation(Test.class).expected().equals(throwable.getCause().getClass())) {
                throw new Exception(test.getName() + " is error -> ");
            }
        }
        for (Method method : after) {
            ReflectionHelper.callMethod(object, method);
        }
    }

    public static void exec(Class testClazz, Object... args) throws Throwable{
        Method[] before = ReflectionHelper.getMethodsByAnnotation(testClazz, Before.class);
        Method[] test = ReflectionHelper.getMethodsByAnnotation(testClazz, Test.class);
        Method[] after = ReflectionHelper.getMethodsByAnnotation(testClazz, After.class);

        Object instance = ReflectionHelper.getInstance(testClazz, args);

        for (Method method : test) {
            execTest(instance, before, method, after);
        }
    }

}
