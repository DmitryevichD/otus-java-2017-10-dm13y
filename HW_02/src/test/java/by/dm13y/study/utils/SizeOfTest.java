package by.dm13y.study.utils;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertTrue;

public class SizeOfTest {
    @Test
    public void PrimitiveSizeTest() throws Exception{
        assertTrue("check boolean size", SizeOf.getSizeOf(true)== 1);
        assertTrue("check byte size", SizeOf.getSizeOf((byte)1)== 1);
        assertTrue("check short size", SizeOf.getSizeOf((short)1)== 2);
        assertTrue("check char size", SizeOf.getSizeOf('a')== 2);
        assertTrue("check float size", SizeOf.getSizeOf(1.4f)== 4);
        assertTrue("check int size", SizeOf.getSizeOf(10)== 4);
        assertTrue("check long size", SizeOf.getSizeOf(10l)== 8);
        assertTrue("check double size", SizeOf.getSizeOf(10.1)== 8);
    }

    @Test
    public void PrimitiveSizeByNameTest() throws Exception {
        assertTrue("check boolean size", SizeOf.getPrimSizeBy(boolean.class.getName())== 1);
        assertTrue("check byte size", SizeOf.getPrimSizeBy(byte.class.getName()) == 1);
        assertTrue("check short size", SizeOf.getPrimSizeBy(char.class.getName()) ==2);
        assertTrue("check char size", SizeOf.getPrimSizeBy(short.class.getName())== 2);
        assertTrue("check float size", SizeOf.getPrimSizeBy(int.class.getName())== 4);
        assertTrue("check int size", SizeOf.getPrimSizeBy(float.class.getName())== 4);
        assertTrue("check long size", SizeOf.getPrimSizeBy(long.class.getName())== 8);
        assertTrue("check double size", SizeOf.getPrimSizeBy(double.class.getName())== 8);
    }

    @Test
    public void PrimitiveArraySizeTest() throws Exception{
        SizeOf.ArchJRE jre = SizeOf.ArchJRE.x86;
        assertTrue("check boolean array", SizeOf.getSizeOfPrimArray(new boolean[1024], jre) == 1040);
        assertTrue("check byte array", SizeOf.getSizeOfPrimArray(new byte[1024], jre) == 1040);
        assertTrue("check char array", SizeOf.getSizeOfPrimArray(new char[1024], jre) == 2064);
        assertTrue("check short array", SizeOf.getSizeOfPrimArray(new short[1024], jre) == 2064);
        assertTrue("check int array", SizeOf.getSizeOfPrimArray(new int[1024], jre) == 4112);
        assertTrue("check float array", SizeOf.getSizeOfPrimArray(new float[1024], jre) == 4112);
        assertTrue("check long array", SizeOf.getSizeOfPrimArray(new long[1024], jre) == 8208);
        assertTrue("check double array", SizeOf.getSizeOfPrimArray(new double[1024], jre) == 8208);
    }

    @Test
    public void ObjectSizeTest() throws Exception{
        SizeOf.ArchJRE jre = SizeOf.ArchJRE.x86;
        assertTrue("check Boolean", SizeOf.getObjectSizeOf(new Boolean(true), jre) == 16);
        assertTrue("check Byte", SizeOf.getObjectSizeOf(new Byte((byte)1), jre) == 16);
        assertTrue("check Char", SizeOf.getObjectSizeOf(new Character('.'), jre) == 16);
        assertTrue("check Short", SizeOf.getObjectSizeOf(new Short((short)1), jre) == 16);
        assertTrue("check Integer", SizeOf.getObjectSizeOf(new Integer(1), jre) == 16);
        assertTrue("check Float", SizeOf.getObjectSizeOf(new Float(1), jre) == 16);
        assertTrue("check Long", SizeOf.getObjectSizeOf(new Long(1), jre) == 24);
        assertTrue("check Double", SizeOf.getObjectSizeOf(new Double(1), jre) == 24);
        assertTrue("check String", SizeOf.getObjectSizeOf(new String(""), jre) == 40);
        assertTrue("check BitSet", SizeOf.getObjectSizeOf(new BitSet(1024), jre) == 168);
        assertTrue("check Boolean Array", SizeOf.getObjectSizeOf(new Boolean[1024], jre) == 4112);
        assertTrue("check Byte Array", SizeOf.getObjectSizeOf(new Byte[1024], jre) == 4112);
        assertTrue("check String Array", SizeOf.getObjectSizeOf(new String[1024], jre) == 4112);
    }
}
