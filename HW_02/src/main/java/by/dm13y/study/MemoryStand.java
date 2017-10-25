package by.dm13y.study;

public interface MemoryStand {
    void setObject(Object obj) throws IllegalArgumentException;
    void makeTest() throws Exception;
    long getResult();
}
