package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;
import java.lang.reflect.Method;

public class TestExecutor {

    private static void execTest(Object object, Method[] before, Method test, Method[] after) throws UnsupportedOperationException{
        for (Method method : before) {
            ReflectionHelper.callMethod(object, method);
        }
        ReflectionHelper.callMethod(object, test);
        for (Method method : after) {
            ReflectionHelper.callMethod(object, method);
        }
    }

    public static void exec(Class testClazz, Object... args) throws UnsupportedOperationException{
        Method[] before = ReflectionHelper.getMethodsByAnnotation(testClazz, Before.class);
        Method[] test = ReflectionHelper.getMethodsByAnnotation(testClazz, Test.class);
        Method[] after = ReflectionHelper.getMethodsByAnnotation(testClazz, After.class);

        Object instance = ReflectionHelper.getInstance(testClazz, args);

        for (Method method : test) {
            execTest(instance, before, method, after);
        }
    }

}
