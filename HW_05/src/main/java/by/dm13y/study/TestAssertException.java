package by.dm13y.study;

public class TestAssertException extends AssertionError {
    public TestAssertException(String msg){
        super(msg == null ? "Test execution error" : msg);
    }
}