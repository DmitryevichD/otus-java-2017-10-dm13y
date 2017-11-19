package by.dm13y.study;

public class Assert {
    private Assert(){}

    private static void fail(String msg){
        throw new TestAssertException(msg);
    }

    public static void assertTrue(String msg, boolean condition){
        if(!condition) fail(msg);
    }

    public static void assertTrue(boolean condition){
        if(!condition) fail(null);
    }
}
