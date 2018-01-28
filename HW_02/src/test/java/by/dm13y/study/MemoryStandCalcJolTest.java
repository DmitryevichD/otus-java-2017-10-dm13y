package by.dm13y.study;
import org.junit.Test;
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
