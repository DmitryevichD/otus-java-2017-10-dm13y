package by.dm13y.study;

import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;

import java.lang.reflect.Method;

public class TestExecutor {

    public static void run(Class testClazz, Object... args){
        Method[] methods = ReflectionHelper.getMethodsByAnnotation(testClazz, Test.class);
        TestCls instance = (TestCls)ReflectionHelper.getInstance(testClazz, args);
        for (Method method : methods) {
            if (method.getParameterCount() > 0) {
                throw new UnsupportedOperationException("Invoke method with parameters unsupported!!!");
            }
            ReflectionHelper.callMethod(instance, method);
        }
    }

}
