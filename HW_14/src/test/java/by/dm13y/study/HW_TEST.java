package by.dm13y.study;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;

public class HW_TEST {

    private void sorterTest(int COUNT_THREAD) {
        int ARRAY_COUNT = 100;
        int THREAD_COUNT = COUNT_THREAD;

        Random rand = new Random(40);

        List<Integer> originalList = new ArrayList<>(ARRAY_COUNT);
        List<Integer> mergeQuickSort = new ArrayList<>(ARRAY_COUNT);
        List<Integer> quickSort = new ArrayList<>(ARRAY_COUNT);
        List<Integer> fjpquickSort = new ArrayList<>(ARRAY_COUNT);

        for (int i = 0; i < ARRAY_COUNT; i++) {
            int element = rand.nextInt(500);
            originalList.add(element);
            fjpquickSort.add(element);
            mergeQuickSort.add(element);
            quickSort.add(element);
        }

        Comparator<Integer> comparator = (o1, o2) -> o1 - o2;

        Sorter<Integer> fjpQuickSorter = new ForkJoinSortExecutor<>(THREAD_COUNT);

        long fjpqStart = System.nanoTime();
        Integer[] fjpqResult = fjpQuickSorter.sort(quickSort, comparator).toArray(new Integer[ARRAY_COUNT]);
        long fjpqEnd = System.nanoTime();

        long origStart = System.nanoTime();
        Integer[] originalResult = originalList.stream().sorted(comparator).toArray(Integer[]::new);
        long origEnd = System.nanoTime();

        Sorter<Integer> mergeQuickSorter = new MergeQuickSorterImpl<>(THREAD_COUNT);

        long mqStart = System.nanoTime();
        Integer[] mqResult = mergeQuickSorter.sort(mergeQuickSort, comparator).toArray(new Integer[ARRAY_COUNT]);
        long mqEnd = System.nanoTime();

        Sorter<Integer> quickSorter = new QuickSorterImpl<>(THREAD_COUNT);

        long qStart = System.nanoTime();
        Integer[] qResult = quickSorter.sort(quickSort, comparator).toArray(new Integer[ARRAY_COUNT]);
        long qEnd = System.nanoTime();



        //checking
        assertArrayEquals(originalResult, mqResult);
        assertArrayEquals(originalResult, qResult);
        assertArrayEquals(originalResult, fjpqResult);


        System.out.println("simple stream  sort: " + String.valueOf(origEnd - origStart));
        System.out.println(" ============ COUNT_THREAD:" + COUNT_THREAD + " ============ ");
        System.out.println("    merge+quick sort: " + String.valueOf(mqEnd - mqStart));
        System.out.println("          quick sort: " + String.valueOf(qEnd - qStart));
        System.out.println("fork join pool quick: " + String.valueOf(fjpqEnd - fjpqStart));
        System.out.println("=============================================================");
    }

    @Test
    public void sorterTest() throws Exception {
        sorterTest(1);
        sorterTest(2);
        sorterTest(4);
        sorterTest(8);
        sorterTest(16);
        sorterTest(32);

    }
}
