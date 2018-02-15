package by.dm13y.study;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class QuickSorterImpl<E> implements Sorter<E> {
    private volatile E[] innerArray;
    private final ExecutionService executionService;


    public QuickSorterImpl(int countThread) {
        if (countThread < 1) {
            throw new IllegalArgumentException("argument must be greater than zero");
        }
        executionService = new ExecutionService(countThread);
    }

    @Override
    public List<E> sort(List<E> list, Comparator<E> comparator) {
        innerArray = (E[])list.toArray();
        CompletableFuture<Void> result = executionService.execute(new ThreadSortExecutor(innerArray, comparator, 0, innerArray.length - 1, executionService));
        try {
            result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList(innerArray);
    }
}
