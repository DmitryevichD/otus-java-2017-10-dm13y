package by.dm13y.study;

/**
 * -XX:-UseCompressedOops -Xmx64m -Xms64m -XX:+PrintGCDetails
 */
public class MemoryStandCalcGC implements MemoryStand{
    private static Object obj;
    private static long objectSize;
    private static Runtime rt = Runtime.getRuntime();
    private static final int SIZE = 100_000;

    public void setObject(Object obj) throws IllegalArgumentException {
        if(obj == null){
            throw new IllegalArgumentException("argument is null");
        }
        if(obj.getClass().isPrimitive()){
            throw new IllegalArgumentException("argument is primitive");
        }
        MemoryStandCalcGC.obj = obj;
    }

    public Object makeObject() throws Exception{
        if(obj instanceof ObjectInitializer)
            return ((ObjectInitializer) obj).customInitObject();
        else {
            return obj.getClass().newInstance();
        }
    }

    @Override
    public void makeTest() throws Exception{
        final Object[] objects = new Object[SIZE];
        rt.gc();
        long memBeforeTest = busyMemory();

        for (int i = 0; i < SIZE; i++) {
            objects[i] = makeObject();
        }
        rt.gc();
        objectSize = (busyMemory() - memBeforeTest) / SIZE;
        for (Object object : objects) {
            object = null;
        }
    }

    @Override
    public long getResult() {
        return objectSize;
    }

    private static long busyMemory(){
        return rt.totalMemory() - rt.freeMemory();
    }
}
