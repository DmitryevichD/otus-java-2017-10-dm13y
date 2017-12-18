package by.dm13y.study.utils;

public class EntityColumn<T> {
    private final T value;
    private final String name;
    private final Class clazz;

    public EntityColumn(String name, T value, Class clazz){
        this.name = name;
        this.value = value;
        this.clazz = clazz;
    }

    public String getCouple(){
        return name + " = " + getValue();
    }

    public T getValue(){
        return value;
    }

    public String getName(){
        return name;
    }

    public String getSQLValue(){
        if (value instanceof Number){
            return String.valueOf(value);
        }else {
            return "'" + String.valueOf(value) + "'";
        }
    }
}
