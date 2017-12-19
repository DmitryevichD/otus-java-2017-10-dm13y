package by.dm13y.study.utils;

public class EntityColumn<T> {
    private final T value;
    private final String name;

    public EntityColumn(String name, T value){
        this.name = name;
        this.value = value;
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
