package by.dm13y.study;

public class MemoryStandCalcGC implements MemoryStand {
    private static Class clazz;
    private static long objectSize;
    private static Runtime rt = Runtime.getRuntime();
    private static long heapMemoryBeforeTest = 0;
    private static long heapMemoryAfterTest = 0;

    @Override
    public void setObject(Object obj) throws IllegalArgumentException {
        if(obj == null){
            throw new IllegalArgumentException("argument is null");
        }
        if(obj.getClass().isPrimitive()){
            throw new IllegalArgumentException("argument is primitive");
        }
        clazz = obj.getClass();
    }

    @Override
    public void makeTest() throws Exception{
        callGC();
        final int arraySize = 10000;
        Object[] objects = new Object[arraySize];
        for (int i = -1; i < arraySize; i++) {
            Object object = null;
            object = clazz.newInstance();
            if(i == -1){
                object = null;
                callGC();
                heapMemoryBeforeTest = busyMemory();
            }else {
                objects[i] = object;
            }
        }
        callGC();
        heapMemoryAfterTest = busyMemory();
        objectSize = (heapMemoryAfterTest - heapMemoryBeforeTest) / arraySize;
    }

    @Override
    public long getResult() {
        return objectSize;
    }

    private static void callGC() throws Exception{
        final int GC_countCalls = 30;
        for (int i = 0; i < GC_countCalls; i++) {
            rt.runFinalization();
            rt.gc();
            Thread.currentThread().yield();
        }
    }

    private static long busyMemory(){
        return rt.totalMemory() - rt.freeMemory();
    }
}
