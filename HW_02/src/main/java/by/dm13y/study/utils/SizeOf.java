package by.dm13y.study.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SizeOf {
    public final static int OBJ_ARRAY_FIELD_SIZE = 4;
    public final static  int OBJ_REF_FIELD_SIZE = 4;

    public static int getPrimSizeBy(String typeName){
        switch (typeName.toLowerCase()){
            case "boolean": return 1;
            case "byte" :return 1;
            case "short" :return 2;
            case "char": return 2;
            case "float": return 4;
            case "int": return 4;
            case "long": return 8;
            case "double": return 8;
            default: return 0;
        }
    }

    public static int getSizeOf(int val) { return getPrimSizeBy(int.class.getName());}

    public static int getSizeOf(float val) {return getPrimSizeBy(float.class.getName());}

    public static int getSizeOf(long val) {return  getPrimSizeBy(long.class.getName());}

    public static int getSizeOf(double val) {return getPrimSizeBy(double.class.getName());}

    public static int getSizeOf(byte val){ return getPrimSizeBy(byte.class.getName());}

    public static int getSizeOf(boolean val){ return getPrimSizeBy(boolean.class.getName());}

    public static int getSizeOf(short val){return getPrimSizeBy(short.class.getName());}

    public static int getSizeOf(char val){return getPrimSizeBy(char.class.getName());}

    private static int getObjPadding(int objectSize){
        return 8 - (objectSize % 8);
    }

    public static int getSizeOfPrimArray(Object prArray, ArchJRE jre){
        int jreHeaderSize = jre.getHeaderSize();
        if(prArray instanceof boolean[]){
            int prTypeSize = getPrimSizeBy(boolean.class.getName());
            int arraySize = ((boolean[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof byte[]){
            int prTypeSize = getPrimSizeBy(byte.class.getName());
            int arraySize = ((byte[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof short[]){
            int prTypeSize = getPrimSizeBy(short.class.getName());
            int arraySize = ((short[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof char[]){
            int prTypeSize = getPrimSizeBy(char.class.getName());
            int arraySize = ((char[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof int[]){
            int prTypeSize = getPrimSizeBy(int.class.getName());
            int arraySize = ((int[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof float[]){
            int prTypeSize = getPrimSizeBy(float.class.getName());
            int arraySize = ((float[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof long[]){
            int prTypeSize = getPrimSizeBy(long.class.getName());
            int arraySize = ((long[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }

        if(prArray instanceof double[]){
            int prTypeSize = getPrimSizeBy(double.class.getName());
            int arraySize = ((double[])prArray).length;
            int prArraySize = jreHeaderSize + OBJ_ARRAY_FIELD_SIZE + arraySize * prTypeSize;
            return prArraySize + getObjPadding(prArraySize);
        }
        return 0;
    };

    private static int getSizeOfArray(Object obj, ArchJRE jre) throws Exception{
        if(obj instanceof Object[]){
            int objSize = jre.header_size;
            objSize += OBJ_ARRAY_FIELD_SIZE;
            int arrLength = ((Object[]) obj).length;
            objSize += arrLength * OBJ_REF_FIELD_SIZE;
            return objSize + getObjPadding(objSize);
        }else{
            return getSizeOfPrimArray(obj, jre);
        }
    }

    public static int getObjectSizeOf(Object obj, ArchJRE jre) throws Exception{
        int objSize = 0;
        if(obj == null) return objSize;
        objSize += jre.getHeaderSize();
        Class clazz = obj.getClass();
        if(clazz.isArray()){
            return getSizeOfArray(obj, jre);
        }else{
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(!Modifier.isStatic(field.getModifiers())){
                    field.setAccessible(true);
                    Class fClazz = field.getType();
                    if (fClazz.isPrimitive()) {
                        objSize += getPrimSizeBy(fClazz.getName());
                    }else{
                        if (fClazz.isArray()) {
                            objSize += OBJ_REF_FIELD_SIZE + getSizeOfArray(field.get(obj), jre);
                        }else {
                            objSize += OBJ_REF_FIELD_SIZE;
                        }
                    }
                }
            }
        }
        return objSize + getObjPadding(objSize);
    }

    public enum ArchJRE{
        x86(8), x64(16);

        private int header_size;

        private ArchJRE(int header_size){
            this.header_size = header_size;
        }

        public int getHeaderSize(){
            return header_size;
        }
    }
}
