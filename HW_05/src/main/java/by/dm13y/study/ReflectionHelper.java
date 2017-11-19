package by.dm13y.study;

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

    static void callMethod(Object object, Method method, Object... args) {
        try {
            method.invoke(object, args);
        }catch (IllegalAccessException | InvocationTargetException ex){
            ex.printStackTrace();
        }
    }

    static private Class<?>[] toParameterTypes(Object... args){
        return Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }

    static public Method[] getMethodsByAnnotation(Class clazz, Class<? extends Annotation> annotation){
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getAnnotation(annotation) != null)
                .toArray(Method[]::new);
    }
}
