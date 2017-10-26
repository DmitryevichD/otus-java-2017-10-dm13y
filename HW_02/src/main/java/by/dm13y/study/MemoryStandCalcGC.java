package by.dm13y.study;

public class MemoryStandCalcGC implements MemoryStand{
    private static Object obj;
    private static long objectSize;
    private static Runtime rt = Runtime.getRuntime();

    public void setObject(Object obj) throws IllegalArgumentException {
        if(obj == null){
            throw new IllegalArgumentException("argument is null");
        }
        if(obj.getClass().isPrimitive()){
            throw new IllegalArgumentException("argument is primitive");
        }
        MemoryStandCalcGC.obj = obj;
    }

    public Object makeObject(){
        if(obj instanceof ObjectInitializer)
            return ((ObjectInitializer) obj).customInitObject();
        else {
            return obj;
        }
    }

    @Override
    public void makeTest() throws Exception{
        callGC();
        final int arraySize = 10000;
        long heapMemoryBeforeTest = 0;
        Object[] objects = new Object[arraySize];
        Object object = null;
        callGC();
        for (int i = -1; i < arraySize; i++) {
            object = makeObject();
            if(i < 0){
                object = null;
                callGC();
                heapMemoryBeforeTest = busyMemory();
            }else {
                objects[i] = object;
            }
        }
        callGC();
        objectSize = (busyMemory() - heapMemoryBeforeTest) / arraySize;
        for (Object object1 : objects) {
            object1 = null;
        }
    }

    @Override
    public long getResult() {
        return objectSize;
    }

    private static void callGC() throws Exception{
        final int GC_countCalls = 20;
        for (int i = 0; i < GC_countCalls; i++) {
            rt.gc();
        }
    }

    private static long busyMemory(){
        return rt.totalMemory() - rt.freeMemory();
    }
}
