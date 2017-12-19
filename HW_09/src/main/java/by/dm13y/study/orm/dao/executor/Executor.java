package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.annotations.Column;
import by.dm13y.study.annotations.Id;
import by.dm13y.study.orm.entity.Entity;
import by.dm13y.study.utils.EntityColumn;
import by.dm13y.study.utils.EntityHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

/**
 * This class is used to mapping {@see by.dm13y.study.orm.entity.Entity} objects to postgres database
 *
 * @implSpec this implementation use pgjdbc >= 9.4.1202
 *           its jdbc driver has a query cache,
 *           works transparently for the application
 *           "server-prepared" activated after the 5th execution
 *           statement is not closable
 *           @see <a href="https://www.slideshare.net/VladimirSitnikv/postgresql-jdbc">non-closing stats</a>
 */

public class Executor {
    private final Connection connection;

    public Executor(Connection conn){
        this.connection = conn;
    }

    public <T extends Entity> void update(T entity) throws Exception {
        String tableName = EntityHelper.getTableName(entity.getClass());
        EntityColumn id = EntityHelper.getId(entity);
        List<EntityColumn> columns = EntityHelper.getColumns(entity);

        String updateSet = String.join(",", columns.stream()
                .map(col -> col.getName() + " = ?")
                .toArray(String[]::new));

        String query = "UPDATE " + tableName + " SET " + updateSet + " WHERE " + id.getName() + " = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        for (int i = 0; i < columns.size(); i++) {
            EntityColumn column = columns.get(i);
            ps.setObject(i + 1, column.getValue());
        }
        ps.setObject(columns.size() + 1, id.getValue());

        ps.execute();
        connection.commit();
    }

    private Long getNewId() throws Exception{
        return query("SELECT max(id) + 1 as id from public.user", rs -> rs.next() ? rs.getLong("id") : null);
    }

    public <T extends Entity> void save(T entity) throws Exception{
        String tableName = EntityHelper.getTableName(entity.getClass());
        Long newId = getNewId();
        entity.setId(newId);

        List<EntityColumn> columns = EntityHelper.getColumns(entity);

        String insertCol = String.join(",", columns.stream()
                .map(EntityColumn::getName)
                .toArray(String[]::new));

        String insertVal = String.join(",", columns.stream()
                .map(EntityColumn::getSQLValue)
                .toArray(String[]::new));

        String query = "INSERT INTO " + tableName + "(" + insertCol + ") values(" + insertVal + ")";

        Statement statement = connection.createStatement();
        statement.execute(query);
        connection.commit();
    }

    public <T extends Entity> T load(Long entityId, Class<T> clazz) throws Exception{
        T loadedEntity = clazz.newInstance();

        String tableName = EntityHelper.getTableName(clazz);
        List<EntityColumn> columns = EntityHelper.getColumns(loadedEntity);
        EntityColumn id = EntityHelper.getId(loadedEntity);

        String columnSet = String.join(",",
                columns.stream()
                        .map(EntityColumn::getName)
                        .filter(colName -> !colName.equals(id.getName()))
                        .toArray(String[]::new));

        String query = "SELECT " + columnSet + " FROM " + tableName + " WHERE " + id.getName() + " = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setObject(1, entityId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Field[] fields = loadedEntity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Column col = field.getAnnotation(Column.class);
                if (col != null && field.getAnnotation(Id.class) == null) {
                    field.set(loadedEntity, rs.getObject(col.name()));
                }
            }
            loadedEntity.setId(entityId);
        }

        return loadedEntity;
    }

    public <T extends Entity> void remove(T entity) throws Exception {
        String tableName = EntityHelper.getTableName(entity.getClass());
        EntityColumn id = EntityHelper.getId(entity);

        String query = "DELETE FROM " + tableName + " WHERE " + id.getName() + " = " + id.getSQLValue();
        query(query, null);
        connection.commit();

    }

    public <T> T query(String query, ResultHandler<T> handler) throws Exception{
        Statement statement = connection.createStatement();
        if(handler != null){
            ResultSet rs = statement.executeQuery(query);
            return handler.handle(rs);
        }else {
            statement.execute(query);
            return null;
        }
    }
}

@FunctionalInterface
interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}

