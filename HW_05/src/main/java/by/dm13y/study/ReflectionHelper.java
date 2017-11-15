package by.dm13y.study;

import by.dm13y.study.annotations.After;

import javax.management.ObjectName;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionHelper {
    private ReflectionHelper(){}

    static <T> T getInstance(Class<T> clazz, Object... args) {
        try {
            return clazz.getConstructor(toParameterTypes(args)).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static Object callMethod(TestCls object, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(object, method, null);
        }catch (IllegalAccessException | InvocationTargetException ex){
            return null;
        }
    }

    static private Class<?>[] toParameterTypes(Object... args){
        return Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }

    static public  <T> Method[] getMethodsByAnnotation(Class clazz, Class<? extends Annotation> annotation){
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(annotation) != null)
                .toArray(Method[]::new);
    }
}
