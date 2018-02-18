package by.dm13y.study;

import java.util.Comparator;
import java.util.concurrent.RecursiveAction;

public class SortExecutor<E> extends RecursiveAction {
    private final int ARRAY_SIZE_FOR_BUBBLE_SORT = 16;
    private final Comparator<E> comparator;
    private final E[] array;
    private final boolean useForkJoinPool;
    private final int minIdx;
    private final int maxIdx;

    public SortExecutor(E[] array, Comparator<E> comparator, int minIdx, int maxIdx, boolean useForkJoinPool){
        this.comparator = comparator;
        this.array = array;
        this.useForkJoinPool = useForkJoinPool;
        this.maxIdx = maxIdx;
        this.minIdx = minIdx;
    }


    @Override
    protected void compute() {
        doQuickSort(minIdx, maxIdx);

    }

    private void swap(int i, int j){
        E tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private boolean isOver(int idx1, int idx2){
        return comparator.compare(array[idx1], array[idx2]) > 0;
    }

    private boolean isLess(int idx1, int idx2){
        return comparator.compare(array[idx1], array[idx2]) < 0;
    }

    private void doBubbleSort(int start, int end){
        for(int i = start; i < end ; i++) {
            for (int j = start; j < end; j++) {
                if (isOver(j, j + 1)) {
                    swap(j, j + 1);
                }
            }
        }
    }

    private void doQuickSort(int start, int end) {
        if (end - start <= ARRAY_SIZE_FOR_BUBBLE_SORT) {
            doBubbleSort(start, end);
            return;
        }

        int lo = start;
        int hi = end;
        int mdl = start - (start - end) / 2;

        while (lo < hi) {
            while (lo < mdl && !isOver(lo, mdl)) {
                lo++;
            }
            while (hi > mdl && isLess(mdl, hi)) {
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

        if (useForkJoinPool) {
            SortExecutor<E> left = new SortExecutor<>(array, comparator, start, mdl, useForkJoinPool);
            SortExecutor<E> right = new SortExecutor<>(array, comparator, mdl + 1, end, useForkJoinPool);
            invokeAll(left, right);
            int i = 10;
        } else {
            doQuickSort(start, mdl);
            doQuickSort(mdl + 1, end);
        }
    }
}
