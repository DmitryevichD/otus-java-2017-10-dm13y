package by.dm13y.study;

import by.dm13y.study.utils.CalcObjRef;
import by.dm13y.study.utils.SizeOf;

import java.util.Set;

public class MemoryStandCalcStatic implements MemoryStand {
    private Object obj;
    private long objectSize;
    private SizeOf.ArchJRE jre = SizeOf.ArchJRE.x86;

    public void setArch(SizeOf.ArchJRE jre){
        this.jre = jre;
    }

    @Override
    public void setObject(Object obj) throws IllegalArgumentException {
        if(obj == null){
            throw new IllegalArgumentException("argument is null");
        }
        if(obj.getClass().isPrimitive()){
            throw new IllegalArgumentException("argument is primitive");
        }
        if(obj.getClass().isArray()){
            if(!(obj instanceof Object[])){
                throw new IllegalArgumentException("argument is a primitive array");
            }
        }
        this.obj = obj;
    }


    @Override
    public void makeTest(){
        objectSize = 0;
        if(obj == null){
            return;
        }
        CalcObjRef cal = new CalcObjRef();
        try {
            Set<Object> objSet = cal.getAllObjRef(obj);
            for (Object obj : objSet) {
                objectSize += SizeOf.getObjectSizeOf(obj, jre);
            }

        } catch (Exception ex) {
            objectSize = 0;
        }

    }

    @Override
    public long getResult() {
        return objectSize;
    }
}
