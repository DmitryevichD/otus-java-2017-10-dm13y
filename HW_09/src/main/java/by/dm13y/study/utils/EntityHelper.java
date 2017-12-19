package by.dm13y.study.utils;

import by.dm13y.study.annotations.Column;
import by.dm13y.study.annotations.Id;
import by.dm13y.study.annotations.Table;
import by.dm13y.study.orm.entity.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class EntityHelper {
    private EntityHelper(){}

    @SuppressWarnings("unchecked")
    public static <T extends Entity> List<EntityColumn> getColumns(T entity) {
        List<EntityColumn> columns = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                try {
                    columns.add(new EntityColumn(column.name(), field.get(entity)));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return columns;
    }


    @SuppressWarnings("unchecked")
    public static <T extends Entity> EntityColumn getId(T entity)  {
        EntityColumn id = null;
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Id id_column = field.getAnnotation(Id.class);
            if (id_column != null) {
                try {
                    id = new EntityColumn(field.getAnnotation(Column.class).name(), field.get(entity));
                    break;
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return id;
    }

    public static  <T extends Entity> String getTableName(Class<T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table.schema() + "." + table.name();
    }
}
