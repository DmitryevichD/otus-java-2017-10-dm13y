package by.dm13y.study;

import org.openjdk.jol.info.ClassLayout;

public class MemoryStandCalsJol implements MemoryStand{
    public Object obj;
    private static long objectSize;

    @Override
    public void setObject(Object obj) throws IllegalArgumentException {
        if (obj == null) throw new IllegalArgumentException("Object is null");
        this.obj = obj;
    }

    public Object makeObject() throws Exception{
        if(obj instanceof ObjectInitializer)
            return ((ObjectInitializer) obj).customInitObject();
        else {
            return obj.getClass().newInstance();
        }
    }

    @Override
    public void makeTest() throws Exception {
        objectSize = ClassLayout.parseInstance(makeObject()).instanceSize();
    }

    @Override
    public long getResult() {
        return objectSize;
    }
}
