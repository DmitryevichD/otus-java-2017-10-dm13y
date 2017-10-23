package by.dm13y.study;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjGraf {
    private Map<Object, ObjectStructure> objGraf = new HashMap<>();

    class ObjectStructure{
        private int header;
        private boolean isArray;
        private Map<String, Integer> primitive;
        private int countReferences = 0;
    }

    public void addObj(Object object){
        if(object.is)
        objGraf.put(object, objectInfo);
    }

    public void addRef() {
        ObjectInfo obj = new ObjectInfo("reference", 4);
        objGraf.put(new Object(), obj);
    }

    public void addPrimitive(String name, int size){
        ObjectInfo obj = new ObjectInfo(name, size);
        objGraf.put(new Object(), obj);
    }

    private void addheader(){
        objGraf.put(new Object(), new ObjectInfo("header", 8));
    }

    private void addArrayShift(int sourceSize){
        objGraf.put(new Object(), new ObjectInfo("arraySize", 4));
    }

    class ObjectInfo{
        private String name;
        private int size;
        public ObjectInfo(String name, int size){
            this.name = name;
            this.size = size;
        }
    }

    private void fillGraf(Object object) throws Exception {
        addheader();
        if (object.getClass().isArray()) {
            addRef();
            if(object instanceof Object[]){

            }else{
                addPrimitive(object.getClass().getName(), SizeUtils.primitiveArraySizeOf(object));
            }
        }else {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.getClass().isPrimitive()) {
                        String typeName = field.getType().getName();
                        addPrimitive(field.getName(), SizeUtils.getPrimitiveSizeByName(typeName));
                    } else {
                        addRef();
                        fillGraf(field.get(object));
                    }
                }
            }
        }
    }



    public void buildGraf(Object object) {
        objGraf = new HashMap<>();
        fillGraf(object);
    }
}
