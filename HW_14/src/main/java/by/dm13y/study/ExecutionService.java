package by.dm13y.study;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ExecutionService {
    private final ThreadPoolExecutor pool;
    private final int nThreads;
    private final Lock lock = new ReentrantLock();

    public ExecutionService(int nThreads){
        pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(nThreads);
        this.nThreads = nThreads;
    }

    public CompletableFuture<Void> execute(ThreadSortExecutor sortExecutor){
        CompletableFuture<Void> result = null;
        lock.lock();
        try {
            if (!isBusy()) {
                result = CompletableFuture.runAsync(sortExecutor, pool);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        lock.unlock();
        return result;
    }


    private boolean isBusy(){
        return pool.getActiveCount() == nThreads;
    }

}
