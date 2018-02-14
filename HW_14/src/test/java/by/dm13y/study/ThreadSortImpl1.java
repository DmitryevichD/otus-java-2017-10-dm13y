package by.dm13y.study;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ThreadSortImpl1 {

    @Test
    public void test1(){
        int arraySize = 100;
        Random rand = new Random(100);
        List<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            list.add(rand.nextInt(200));
        }

        ThreadSorter<Integer> threadSorter = new ThreadSorter<>(2);
        threadSorter.sort(list, (o1, o2) -> o1 - o2);
    }

}