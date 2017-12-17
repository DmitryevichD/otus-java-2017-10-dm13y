package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.annotations.Column;
import by.dm13y.study.orm.entity.DataSet;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Executor {
    private Connection connection;

    public Executor(Connection conn){
        this.connection = conn;
    }

    private List<ColumnDB> getColumns(DataSet dataSet) {
        List<ColumnDB> columns = new ArrayList<>();
        Field[] fields = dataSet.getClass().getFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                try {
                    columns.add(new ColumnDB(column.name(), field.get(dataSet), field.getType()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return columns;
    }

    private String toUpdateSetString(List<ColumnDB> columns) {
        return String.join(",", columns.stream()
                .map(col -> col.getCouple())
                .toArray(String[]::new));
    }

    public void execUpdate(@NotNull DataSet dataSet) {
        long id = dataSet.getId();
        String tableName = dataSet.getTableName();
        String idColumnName = dataSet.getIdColumnName();

        String query = "UPDATE " + tableName + " SET " +
                toUpdateSetString(getColumns(dataSet)) + "WHERE " + idColumnName + " = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException ex) {
            //preparedStatement
            //todo: Описать почему не закрываем статемент
            ex.printStackTrace();
        }

    }

    public <T> T execQuery(String query, ResultHandler<T> handler){
        return null;
    }

    class ColumnDB{
        private final Object value;
        private final String name;
        private final Class clazz;

        public ColumnDB(String name, Object value, Class clazz){
            this.name = name;
            this.value = value;
            this.clazz = clazz;
        }

        public String getCouple(){
            return name + " = " + getValue();
        }

        public String getValue(){
            if(value instanceof Number){
                return String.valueOf(value);
            }else {
                return "'" + String.valueOf(value) + "'";
            }
        }
    }
}
