package by.dm13y.study;

import by.dm13y.study.utils.SizeOf;
import org.junit.Before;
import org.junit.Test;

public class MemoryStandWarOfImpl {
    private SizeOf.ArchJRE jre;
    private MemoryStand standStatic = new MemoryStandCalcStatic();
    private MemoryStand standGC;

    @Before
    public void checkJRE(){
        String curJRE = System.getProperty("sun.arch.data.model");
        if (curJRE.contains("64")) {
            jre = SizeOf.ArchJRE.x64;
        }else {
            jre = SizeOf.ArchJRE.x86;
        }
        System.out.println("JRE:" + curJRE);
    }

    @Test
    public void containerSimpleObjectTest() throws Exception{
        standGC = new MemoryStandCalcGC();
        ObjectInitializer obj = ()  -> new Integer(1);
        standGC.setObject(obj);
        standGC.makeTest();

        standStatic.setObject(new Integer(1));
        standStatic.makeTest();

        System.out.println("\nInteger");
        System.out.println("    GC Stand SIZE: " + standGC.getResult());
        System.out.println("Static Stand SIZE: " + standStatic.getResult());
    }

    @Test
    public void containerStringTest() throws Exception{
        standGC = new MemoryStandCalcGC();
        for (int i = 5; i < 10000000; i=i*5) {
            final Integer val = i;

            ObjectInitializer obj = ()  -> {return new String() + val;}; //trick for compiler
            standGC.setObject(obj);
            standGC.makeTest();
            standGC.makeTest();


            standStatic.setObject(new String() + val);
            standStatic.makeTest();

            System.out.println("\nString: '" + val +"'");
            System.out.println("    GC Stand SIZE: " + standGC.getResult());
            System.out.println("Static Stand SIZE: " + standStatic.getResult());
        }
    }

    @Test
    public void containerArrayTest() throws Exception{
        standGC = new MemoryStandCalcGC();
        for (int i = 1; i < 1000; i++) {
            final Integer arraySize = i;
            final Integer[] array = new Integer[arraySize];

            ObjectInitializer obj = ()  -> {return new Integer[arraySize];};
            standGC.setObject(obj);
            standGC.makeTest();

            standStatic.setObject(array);
            standStatic.makeTest();

            System.out.println("\nInteger array SIZE: '" + arraySize +"'");
            System.out.println("    GC Stand SIZE: " + standGC.getResult());
            System.out.println("Static Stand SIZE: " + standStatic.getResult());
        }
    }

}
