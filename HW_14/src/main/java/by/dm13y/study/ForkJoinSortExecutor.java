package by.dm13y.study;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinSortExecutor<E> implements Sorter<E> {
    private volatile E[] innerArray;
    private final ForkJoinPool forkJoinPool;

    public ForkJoinSortExecutor(int countThread) {
        if (countThread < 1) {
            throw new IllegalArgumentException("argument must be greater than zero");
        }
        forkJoinPool = new ForkJoinPool(countThread);
    }

    @Override
    public List<E> sort(List<E> list, Comparator<E> comparator) {
        innerArray = (E[])list.toArray();
        forkJoinPool.invoke(new SortExecutor<>(innerArray, comparator, 0, list.size() - 1, true));
        return Arrays.asList(innerArray);
    }
}
