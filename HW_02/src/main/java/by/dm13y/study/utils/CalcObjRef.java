package by.dm13y.study.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class CalcObjRef {
    private Set<Object> objRefSet;

    private void calcCountRef(Object obj) throws IllegalAccessException{
        if (obj == null) return;
        Class clazz = obj.getClass();
        if(clazz.isPrimitive())return;
        if(clazz.isArray()){
            if(obj instanceof Object[]){ //it's an array of objects
                Object[] objArray = (Object[]) obj;
                for (int i = 0; i < objArray.length; i++) {
                    calcCountRef(objArray[i]);
                }
            }
        }else{
            objRefSet.add(obj);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(!Modifier.isStatic(field.getModifiers())){
                    Class fieldClazz = field.getType();
                    if(!fieldClazz.isPrimitive()){
                        field.setAccessible(true);
                        calcCountRef(field.get(obj));
                    }
                }
            }
        }
    }

    public Set<Object> getAllObjRef(Object obj) throws IllegalAccessException{
        objRefSet = new HashSet<>();
        objRefSet.add(obj);
        calcCountRef(obj);
        return objRefSet;
    }

}
