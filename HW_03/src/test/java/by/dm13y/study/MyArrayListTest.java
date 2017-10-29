package by.dm13y.study;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyArrayListTest {

    @Test
    public void containsTest() throws Exception{
        List<Integer> myArray = new MyArrayList<>();
        int maxVal = 1000;
        for (int i = 0; i < 1000; i++) {
           myArray.add(i);
        }
        for (int i = 0; i < maxVal * 2; i++) {
            if(i < maxVal){
                assertTrue(myArray.contains(i));
            }else {
                assertFalse(myArray.contains(i));
            }
        }
    }

    @Test
    public void removeAllTest() throws Exception {
        List<Integer> origArray = new ArrayList<>();
        List<Integer> testArray = new MyArrayList<>();
        List<Integer> remArray = new ArrayList<>();
        int startRemLimit = 5;
        int endRemLimit = 50;
        for (int i = 0; i < 100; i++) {
            origArray.add(i);
            testArray.add(i);
            if(i >= startRemLimit && i <= endRemLimit){
                remArray.add(i);
            }
        }

        origArray.removeAll(remArray);
        testArray.removeAll(remArray);
        assertTrue(origArray.size() == testArray.size());
        testArray.removeAll(origArray);
        assertTrue(testArray.isEmpty());
    }

    @Test
    public void retainAllTest() throws Exception{
        List<Integer> dublArray = new ArrayList<>();
        List<Integer> testArray = new MyArrayList<>();
        int startRetainLimit = 5;
        int endRetainLimit = 100;
        for (int i = 0; i < 1000; i++) {
            testArray.add(i);
            if(i >= startRetainLimit && i <=endRetainLimit){
                dublArray.add(i);
            }
        }
        testArray.retainAll(dublArray);
        for (Integer integer : testArray) {
            assertTrue(dublArray.contains(integer));
        }
        testArray.removeAll(dublArray);
        assertTrue(testArray.isEmpty());
    }

    @Test
    public void cleanTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testArray.add(i);
        }
        testArray.clear();
        assertTrue(testArray.isEmpty());
    }

    @Test
    public void getSetTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            testArray.add(i);
        }
        for (int i = 1000; i >= 0; i--) {
            testArray.set(i, i);
            assertTrue(testArray.get(i) == i);
        }
    }

    @Test
    public void addTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testArray.add(i);
        }
        int testVal = -999;
        testArray.add(500, testVal);
        assertTrue(testArray.size() == 1001);
        assertTrue(testArray.get(500) == testVal);
        for(int i = 501; i < testArray.size(); i++) {
            assertTrue(testArray.get(i) == i - 1);
        }
    }

    @Test
    public void removeTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testArray.add(i);
        }
        int remVal = testArray.remove(10);
        assertTrue(testArray.size() == 1000 - 1);
        assertTrue(testArray.contains(remVal) == false);
    }

    @Test
    public void subListTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testArray.add(i);
        }
        List<Integer> subList = testArray.subList(50, 80);
        for (int i = 50; i <= 80; i++) {
            assertTrue(subList.contains(i));
        }
        assertTrue(subList.size() == 31);
        assertTrue(testArray.size() == 1000);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void ExceptionTest() throws Exception{
        List<Integer> testArray = new MyArrayList<>();
        testArray.get(10);
        testArray.subList(10, 200);
        testArray.add(30, 40);
    }

}