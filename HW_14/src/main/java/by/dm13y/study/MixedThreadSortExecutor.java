package by.dm13y.study;

import java.util.Comparator;

public class MixedThreadSortExecutor<E> implements Runnable {
    private final E[] array;
    private final Comparator<E> comparator;
    private final int minIdx;
    private final int maxIdx;
    private final int ARRAY_SIZE_FOR_BUBBLE_SORT = 16;

    public MixedThreadSortExecutor(E[] array, Comparator<E> comparator, int minIdx, int maxIdx){
        this.array = array;
        this.comparator = comparator;
        this.minIdx = minIdx;
        this.maxIdx = maxIdx;
    }

    private void swap(int i, int j){
        E tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void doBubbleSort(int start, int end){
        for(int i = start; i < end ; i++) {
            for (int j = start; j < end; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }

    private void doQuickSort(int start, int end){
        if (end - start <= ARRAY_SIZE_FOR_BUBBLE_SORT) {
            doBubbleSort(start, end);
            return;
        }
        int lo = start;
        int hi = end;
        int mdl = start - (start - end) / 2;

        while (lo < hi) {
            while (lo < mdl && (comparator.compare(array[lo], array[mdl]) <= 0)) {
                lo++;
            }
            while (hi > mdl && (comparator.compare(array[mdl], array[hi]) <= 0)) {
                hi--;
            }

            if (lo < hi) {
                swap(lo, hi);
                if (lo == mdl) {
                    mdl = hi;
                } else if (hi == mdl) {
                    mdl = lo;
                }
            }
        }
        doQuickSort(start, mdl);
        doQuickSort(mdl + 1, end);
    }


    @Override
    public void run() {
        doQuickSort(minIdx, maxIdx);
    }
}
