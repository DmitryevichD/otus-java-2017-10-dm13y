package by.dm13y.study;

import java.util.Comparator;

public class SubArraySorter<E> extends Thread {
    private final int countElementForBubbleSort = 8;
    private final E[] array;
    private final Comparator<E> comparator;
    private final int minIdx;
    private final int maxIdx;

    public SubArraySorter(E[] array, Comparator<E> comparator, int minIndex, int maxIndex) {
        this.array = array;
        this.comparator = comparator;
        this.minIdx = minIndex;
        this.maxIdx = maxIndex;
    }

    @Override
    public void run(){
        doQuickSort(minIdx, maxIdx);
    }

    private void doBubbleSort(int start, int end) {
        for(int i = start; i <= end; i++){
            for(int j = start + 1; j <= (start - i); j++) {
                if(comparator.compare(array[i], array[j]) < 0) {
                    E tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }

    private void  doQuickSort(int start, int end) {
        if (start > end) {
            return;
        }
        if (end - start <= countElementForBubbleSort) {
            doBubbleSort(start, end);
        }

        int i = start;
        int j = end;
        int cur = start - (start - end) / 2;
        while (i < j) {
            while (i < cur && (comparator.compare(array[i], array[cur]) <= 0)) {
                i++;
            }
            while (j > cur && (comparator.compare(array[cur], array[j]) <= 0)) {
                j--;
            }
            if (i < j) {
                E tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
            doQuickSort(start, cur);
            doQuickSort(cur + 1, end);
    }
}
