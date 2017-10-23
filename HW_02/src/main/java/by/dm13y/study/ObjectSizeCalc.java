package by.dm13y.study;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjectSizeCalc {
    private static final BitJRE curArch;
    private static final String total_size_hdr = "total_size:";
    private static final String shift_size_hdr = "shift_size";

    static {
        curArch = System.getProperties().getProperty("os.arch").contains("64") ? BitJRE.x64 : BitJRE.x32;
    }

    public static int getPrimitiveSizeByName(String typeName){
        switch (typeName){
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

    public static int sizeOf(int val) { return getPrimitiveSizeByName(int.class.getName());}

    public static int sizeOf(float val) {return getPrimitiveSizeByName(float.class.getName());}

    public static int sizeOf(long val) {return  getPrimitiveSizeByName(long.class.getName());}

    public static int sizeOf(double val) {return getPrimitiveSizeByName(double.class.getName());}

    public static int sizeOf(byte val){ return getPrimitiveSizeByName(byte.class.getName());}

    public static int sizeOf(boolean val){ return getPrimitiveSizeByName(boolean.class.getName());}

    public static int sizeOf(short val){return getPrimitiveSizeByName(short.class.getName());}

    public static int sizeOf(char val){return getPrimitiveSizeByName(char.class.getName());}

    private static int getObjectSizeShift(int objectSize){
        return 8 - (objectSize % 8);
    }

    private static int getArraySizeOf(int cellSize, int cellCount, BitJRE jre){
        int object_size = jre.getHeaderSize() + 4 + cellSize * cellCount;
        return object_size + getObjectSizeShift(object_size);
    }

    private static int primitiveArraySizeOf(Object val, BitJRE jre){
        if(val instanceof boolean[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(boolean.class.getName()));
            return getArraySizeOf(primitive_size, ((boolean[])val).length, jre);
        }

        if(val instanceof byte[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(byte.class.getName()));
            return getArraySizeOf(primitive_size, ((byte[])val).length, jre);
        }

        if(val instanceof short[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(short.class.getName()));
            return getArraySizeOf(primitive_size, ((short[])val).length, jre);
        }

        if(val instanceof char[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(char.class.getName()));
            return getArraySizeOf(primitive_size, ((char[])val).length, jre);
        }

        if(val instanceof int[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(int.class.getName()));
            return getArraySizeOf(primitive_size, ((int[])val).length, jre);
        }

        if(val instanceof float[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(float.class.getName()));
            return getArraySizeOf(primitive_size, ((float[])val).length, jre);
        }

        if(val instanceof long[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(long.class.getName()));
            return getArraySizeOf(primitive_size, ((long[])val).length, jre);
        }

        if(val instanceof double[]){
            int primitive_size = sizeOf(getPrimitiveSizeByName(double.class.getName()));
            return getArraySizeOf(primitive_size, ((double[])val).length, jre);
        }
        return 0;
    };

    private static int objectArraySizeOf(Object val, BitJRE jre){
        return getArraySizeOf(8, ((Object[]) val).length, jre);
    }

    private static Map<String, Integer> computeFieldSize(Object obj, BitJRE jre) throws Exception{
        Map<String, Integer> result = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if(!Modifier.isStatic(field.getModifiers())){
                if(fieldType.isPrimitive()){
                    String name = "primitive:" + field.getName();
                    Integer size = getPrimitiveSizeByName(fieldType.getName());
                    result.put(name, size);
                }else { //its reference
                    if (fieldType.isArray()) {
                        field.setAccessible(true);
                        Object val = field.get(obj);
                        if (val instanceof Object[]) {
                            String name = "ref array:" + field.getName();
                            Integer size = 4;
                            result.put(name, size);
                        } else {
                            String name = "prim_array:" + field.getName();
                            Integer size = primitiveArraySizeOf(val, jre);
                            result.put(name, size);
                        }
                    }else {
                        String name = "reference:" + field.getName();
                        Integer size = 4;
                        result.put(name, size);
                    }
                }
            }
        }
        return result;
    }

    public static Map<String, Integer> detailedSizeOf(Object obj, BitJRE jre) throws Exception{

        Map<String, Integer> detailInfo = new HashMap<>();
        detailInfo.put("header", jre.getHeaderSize());
        if (obj.getClass().isArray()) {
            detailInfo.put("array_size_val", 4);
        }else{
//            detailInfo
        }


        Map<String, Integer> result = computeFieldSize(obj, jre);

        int objSize = 0;
        for (Integer val : result.values()) {
            objSize += val;
        }
        int shiftSize = 8 - (objSize % 8);
        result.put(shift_size_hdr, objSize);
        result.put(total_size_hdr, objSize + shiftSize);
        return result;
    }

    public static Map<String, Integer> detailedSizeOf(Object obj) throws Exception{
        return detailedSizeOf(obj, curArch);
    }

    public static int sizeOf(Object obj, BitJRE jre) throws Exception{
        return detailedSizeOf(obj, jre).get(total_size_hdr);
    }

    public static int sizeOf(Object obj) throws Exception{
        return detailedSizeOf(obj).get(total_size_hdr);
    }



    public enum BitJRE{
        x32(8), x64(16);

        private int headerSize;
        private BitJRE(int headerSize){
            this.headerSize = headerSize;
        }

        public int getHeaderSize(){
            return headerSize;
        }
    }
}
