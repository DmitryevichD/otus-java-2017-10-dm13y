package by.dm13y.study;

public class SizeUtils {
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

    private static int getArraySizeOf(int cellSize, int cellCount, ObjectSizeCalc.BitJRE jre){
        int object_size = jre.getHeaderSize() + 4 + cellSize * cellCount;
        return object_size + getObjectSizeShift(object_size);
    }

    public static int primitiveArraySizeOf(Object val){
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
}
