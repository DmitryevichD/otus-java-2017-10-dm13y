package by.dm13y.study;

import jdk.nashorn.internal.scripts.JO;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MemoryStandCalcJolTest {
    @Test
    public void StringArrayTest() throws Exception{
        ObjectInitializer obj = ()  -> {return new String();};
        MemoryStand jolStand = new MemoryStandCalsJol();
        jolStand.setObject(obj);
        jolStand.makeTest();

        System.out.println(jolStand.getResult());
    }
}
