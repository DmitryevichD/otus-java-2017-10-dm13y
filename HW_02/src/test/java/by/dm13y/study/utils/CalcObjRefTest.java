package by.dm13y.study.utils;

import by.dm13y.study.utils.CalcObjRef;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalcObjRefTest {
    @Test
    public void countObjectArrayTest() throws Exception{
        CalcObjRef calcObjRef = new CalcObjRef();
        assertTrue(calcObjRef.getAllObjRef("").size() == 1);
        assertTrue(calcObjRef.getAllObjRef(new String[1024]).size() == 1);
        assertTrue(calcObjRef.getAllObjRef(new String[]{"1", "2", "3"}).size() == 4);
        assertTrue(calcObjRef.getAllObjRef(new String[]{"1", "1", "3"}).size() == 3);
        assertTrue(calcObjRef.getAllObjRef(new Object[]{null, new Object(), null}).size() == 2);
    }
}