package by.dm13y.study;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class JsonMaker {
    
    private Object[] unpack(Object array){
        Object[] newArray = new Object[Array.getLength(array)];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Array.get(array, i);
        }
        return newArray;
    }

    private void writeArray(Object[] array, JsonWriter out) throws IllegalAccessException  {
        out.beginArray();
        for (Object arrayElement : array) {
            if(arrayElement == null){
                out.arrayValue(arrayElement);
            }else if (arrayElement instanceof Number || (arrayElement.getClass() == String.class)){
                out.arrayValue(arrayElement);
            }else{
                write(arrayElement, arrayElement.getClass(), out);
            }
        }
        out.endArray();
    }

    private void write(Object src, Class cls, JsonWriter out) throws IllegalAccessException {
        if(src == null){return;}
        out.beginObject();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(src) == null) continue;
            out.name(field.getName());
            Class<?> fieldType = field.getType();
            Object value = field.get(src);

            if(fieldType.isPrimitive() || value instanceof Number || value instanceof Character){
                out.value(value);
                continue;
            }

            if(fieldType == String.class){
                out.value(value);
                continue;
            }

            if (fieldType.isArray()) {
                Object[] array = unpack(value);
                writeArray(array, out);
                continue;
            }

            write(value, fieldType, out);
        }
        out.endObject();
    }

    public String toJson(Object obj) throws Exception{
        if (obj == null) { return "null";}
        JsonWriter jw = new JsonWriter();
        write(obj, obj.getClass(), jw);
        return jw.toString();
    }
}
