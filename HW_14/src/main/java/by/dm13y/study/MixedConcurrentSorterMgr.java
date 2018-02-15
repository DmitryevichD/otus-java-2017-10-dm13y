package by.dm13y.study;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MixedConcurrentSorterMgr<E> implements Sorter<E> {
    private final int MIN_SUB_ARRAY_SIZE = 64;
    private final int COUNT_THREADS;
    private final Executor threadPool;
    private volatile E[] innerArray;

    public MixedConcurrentSorterMgr(){
        COUNT_THREADS = 1;
        threadPool = Executors.newFixedThreadPool(COUNT_THREADS);
    }

    public MixedConcurrentSorterMgr(int countThread) {
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

    @Override
    public List<E> sort(List<E> list, Comparator<E> comparator) {
        if(!checkList(list)) return list;
        innerArray = (E[])list.toArray();

        int countWorkers = getCountWorkers(list.size());
        int subArraySize = list.size() / countWorkers;

        Map<CompletableFuture<Void>, Deque<Integer>> futures = new HashMap<>(countWorkers);

        for (int i = 0; i < countWorkers; i++) {
            int minIdx = i == 0 ? 0 : i * subArraySize + 1;
            int maxIdx = i == countWorkers - 1 ? innerArray.length - 1 : (i + 1) * subArraySize;

            Deque<Integer> idxStack = getStack(minIdx, maxIdx);
            CompletableFuture<Void> future = CompletableFuture.runAsync(new MixedThreadSortExecutor<>(innerArray, comparator, minIdx, maxIdx), threadPool);

            futures.put(future, idxStack);
        }

        futures.keySet().forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });

        if(countWorkers > 1){//do merging
            Collection<Deque<Integer>> stacks= futures.values();

            for(int i = 0; i < list.size(); i++){

                E minVal = null;
                boolean itsFirstStack = true;
                Deque<Integer> selStack = null;

                for (Deque<Integer> stack : stacks) {

                    if(itsFirstStack){

                        selStack = stack;
                        minVal = innerArray[selStack.element()];
                        itsFirstStack =false;
                        continue;

                    }

                    E curVal = innerArray[stack.element()];
                    if(comparator.compare(minVal, curVal) > 0){

                        minVal = curVal;
                        selStack = stack;
                    }
                }

                selStack.remove();
                if(selStack.isEmpty()){
                    stacks.remove(selStack);
                }
                list.set(i, minVal);
            }
            return list;
        }else{
            return Arrays.asList(innerArray);
        }
    }

    private Deque<Integer> getStack(int min, int max){
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = min; i <= max; i++) {
            stack.add(i);
        }
        return stack;
    }
}
