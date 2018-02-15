package by.dm13y.study;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class MixedConcurrentSorterMgrTest {
    @Test
    public void mixedSorter() throws Exception {
        int ARRAY_COUNT = 1000000;
        int THREAD_COUNT = 40;

        Random rand = new Random(40);

        List<Integer> originalList = new ArrayList<>(ARRAY_COUNT);
        List<Integer> testList = new ArrayList<>(ARRAY_COUNT);

        for (int i = 0; i < ARRAY_COUNT; i++) {
            int element = rand.nextInt(500);
            originalList.add(element);
            testList.add(element);
        }

        Collections.sort(originalList);

        //My sorter
        Sorter<Integer> sorter = new MixedConcurrentSorterMgr<>(40);

        testList = sorter.sort(testList, (o1, o2) -> o1 - o2);

        assertArrayEquals(testList.toArray(), originalList.toArray());

    }
}