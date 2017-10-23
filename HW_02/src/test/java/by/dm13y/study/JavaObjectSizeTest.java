package by.dm13y.study;

import org.junit.Test;

import java.lang.instrument.Instrumentation;

import static org.junit.Assert.assertTrue;

public class JavaObjectSizeTest {
    class MyTest{
        private Boolean[] BL = new Boolean[100];
        private boolean[] bl = new boolean[100];
    }

//    @Test
    public void PrimitiveSizeTest() throws Exception{
        assertTrue("check boolean size", ObjectSizeCalc.sizeOf(true)== 1);
        assertTrue("check byte size", ObjectSizeCalc.sizeOf((byte)1)== 1);
        assertTrue("check short size", ObjectSizeCalc.sizeOf((short)1)== 2);
        assertTrue("check char size", ObjectSizeCalc.sizeOf('a')== 2);
        assertTrue("check float size", ObjectSizeCalc.sizeOf(1.4f)== 4);
        assertTrue("check int size", ObjectSizeCalc.sizeOf(10)== 4);
        assertTrue("check long size", ObjectSizeCalc.sizeOf(10l)== 8);
        assertTrue("check double size", ObjectSizeCalc.sizeOf(10.1)== 8);
    }

//    @Test
    public void PrimitiveByNameTest() throws Exception {
        assertTrue("check boolean size", ObjectSizeCalc.getPrimitiveSizeByName(boolean.class.getName())== 1);
        assertTrue("check byte size", ObjectSizeCalc.getPrimitiveSizeByName(byte.class.getName()) == 1);
        assertTrue("check short size", ObjectSizeCalc.getPrimitiveSizeByName(char.class.getName()) ==2);
        assertTrue("check char size", ObjectSizeCalc.getPrimitiveSizeByName(short.class.getName())== 2);
        assertTrue("check float size", ObjectSizeCalc.getPrimitiveSizeByName(int.class.getName())== 4);
        assertTrue("check int size", ObjectSizeCalc.getPrimitiveSizeByName(float.class.getName())== 4);
        assertTrue("check long size", ObjectSizeCalc.getPrimitiveSizeByName(long.class.getName())== 8);
        assertTrue("check double size", ObjectSizeCalc.getPrimitiveSizeByName(double.class.getName())== 8);
    }

    @Test
    public void SimpleObjectSizeTest() throws Exception{
//        assertTrue("check Integer size", ObjectSizeCalc.sizeOf(new Integer(1), ObjectSizeCalc.BitJRE.x32) == 16);
//        assertTrue("check Integer size", ObjectSizeCalc.sizeOf(new Integer(1), ObjectSizeCalc.BitJRE.x64) == 24);
        assertTrue("check String array size", ObjectSizeCalc.sizeOf(new String(), ObjectSizeCalc.BitJRE.x32) == 40);
//        assertTrue("check boolean array size", ObjectSizeCalc.sizeOf(new boolean[1024], ObjectSizeCalc.BitJRE.x32) == 4112);

        System.out.println(ObjectSizeCalc.sizeOf(Class.class, ObjectSizeCalc.BitJRE.x32));
        System.out.println(ObjectSizeCalc.detailedSizeOf(new String(), ObjectSizeCalc.BitJRE.x32));
//        System.out.println(ObjectSizeCalc.computeFieldSize(new SimpleTestObject()));
    }




    @Test
    public void objectSize() throws Exception{
        String str = new String();
        String srt1 = new String();
        System.out.println(str == srt1);
    }

}
