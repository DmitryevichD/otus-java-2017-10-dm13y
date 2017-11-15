package by.dm13y.study.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    String value() default "";
    Class<? extends Throwable> throwExecpt() default Test.None.class;

    public static class None extends Throwable {
        private None() {
        }
    }
}
