package by.dm13y.study;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryStandCalcStaticTest {
    @Test
    public void StringArrayTest() throws Exception{
        //
        String[] testStringArray = new String[1024];
        for (int i = 0; i < testStringArray.length; i++) {
            testStringArray[i] = "" + i;
        }
        MemoryStand ms = new MemoryStandCalcStatic();
        ms.setObject(testStringArray);
        ms.makeTest();
        assertTrue("check String array with diff string", ms.getResult() == 53184);
    }
}