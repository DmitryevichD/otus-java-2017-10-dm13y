package by.dm13y.study;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryStandCalcGCTest {

    @Test
    //Results of the test can be diff and calculated depending on the JRE
    public void memoryStandCalcGCTest() throws Exception{
        MemoryStand ms = new MemoryStandCalcGC();
        ms.setObject(new Object());
        ms.makeTest();
        System.out.println(ms.getResult());
    }

}