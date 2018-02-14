package by.dm13y.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class ThreadSorter<E> implements Sorter<E> {
    private final int MIN_SUB_ARRAY_SIZE = 64;
    private final int COUNT_THREADS;
    private final Executor threadPool;
    private volatile E[] innerArray;

    public ThreadSorter(){
        COUNT_THREADS = 1;
        threadPool = Executors.newFixedThreadPool(COUNT_THREADS);
    }

    public ThreadSorter(int countThread) {
        if (countThread < 1) {
            throw new IllegalArgumentException("argument must be greater than zero");
        }
        COUNT_THREADS = countThread;
        threadPool = Executors.newFixedThreadPool(COUNT_THREADS);
    }

    private boolean checkList(List<E> list){
        if (list.isEmpty()) {
            return false;
        }
        if (list.size() == 1){
            return false;
        }
        return true;
    }

    @Override
    public List<E> sort(List<E> list, Comparator<E> comparator){
        if(!checkList(list)) return list;

        int countWorkers = getCountWorkers(list.size());
        int subArraySize = list.size() / countWorkers;

        innerArray = (E[])list.toArray();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < countWorkers; i++) {
            int minIdx = i == 0 ? 0 : i * subArraySize + 1;
            int maxIdx = i == countWorkers - 1 ? innerArray.length - 1 : (i + 1) * subArraySize;
            futures.add(CompletableFuture.runAsync(new SubArraySorter<E>(innerArray, comparator, minIdx, maxIdx), threadPool));
        }

        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println(list);
        System.out.println(innerArray);
        return null;
    }

    public static void main(String[] args) {
        ThreadSorter<String> ss = new ThreadSorter<>(15);
        ss.sort(Arrays.asList(new String[339]), null);
    }

    public int getCountWorkers(int listSize) {
        if (listSize < MIN_SUB_ARRAY_SIZE) {
            return 1;
        }
        if ((listSize / COUNT_THREADS) >= MIN_SUB_ARRAY_SIZE) {
            return COUNT_THREADS;
        }else {
            return listSize / MIN_SUB_ARRAY_SIZE;
        }
    }
}
