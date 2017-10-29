package by.dm13y.study;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class HW_Test {
    List<Integer> myArray;
    @Before
    public void initMyArray(){
        myArray = new MyArrayList<>();
    }

    @Test
    public void collectionsAddAllTest() throws Exception{
        Collections.addAll(myArray, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (int i = 0; i < 10; i++) {
            assertTrue(myArray.contains(i));
        }
        assertTrue(myArray.size() == 10);

        for (int i = 0; i < 1000; i++) {
            Collections.addAll(myArray, i);
        }

        for (int i = 0; i < 1000; i++) {
            assertTrue(myArray.contains(i));
        }
        assertTrue(myArray.size() == 1010);

    }

    @Test
    public void collectionsCopyTest() throws Exception {
        Collections.addAll(myArray, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        Collections.copy(myArray, arrayList);
        assertTrue(myArray.size() == arrayList.size());
        arrayList.retainAll(myArray);
        assertTrue(myArray.size() == arrayList.size());
        arrayList.removeAll(myArray);
        assertTrue(arrayList.isEmpty());
    }

    @Test
    public void collectionsSortTest() throws Exception{
        List<Integer> myList = new MyArrayList<>();
        for (int i = 999; i >= 0; i--) {
            myList.add(i);
        }

        for (int i = 999; i >= 0; i--) {
            assertFalse(myList.get(i) == i);
        }

        Collections.sort(myList);
        for (int i = 0; i < 1000; i++) {
            assertTrue(myList.get(i) == i);
        }
    }

}
