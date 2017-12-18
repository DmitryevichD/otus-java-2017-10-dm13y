package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.orm.entity.Entity;
import by.dm13y.study.utils.EntityColumn;
import by.dm13y.study.utils.EntityHelper;

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

    public <T extends Entity> void update(T entity) {
        String tableName = EntityHelper.getTableName(entity);
        EntityColumn id = EntityHelper.getId(entity);
        List<EntityColumn> columns = EntityHelper.getColumns(entity);

        String updateSet = String.join(",", columns.stream()
                .map(col -> col.getName() + " = ?")
                .toArray(String[]::new));

        String query = "UPDATE " + tableName + " SET " + updateSet + " WHERE " + id.getName() + " = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 0; i < columns.size(); i++) {
                EntityColumn column = columns.get(i);
                ps.setObject(i + 1, column.getValue());
            }
            ps.setObject(columns.size() + 1, id.getValue());

            ps.execute();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Long getId(){
        Long id = select("SELECT max(id) + 1 as id from public.user", rs -> {return rs.next() ? rs.getLong("id") : null;});
        if(id != null){
            return id;
        }else {
            throw new UnsupportedOperationException("id is null");
        }
    }


    public <T extends Entity> void save(T entity){
        String tableName = EntityHelper.getTableName(entity);
        Long newId = getId();
        entity.setId(newId);

        List<EntityColumn> columns = EntityHelper.getColumns(entity);

        String insertCol = String.join(",", columns.stream()
                .map(EntityColumn::getName)
                .toArray(String[]::new));

        String insertVal = String.join(",", columns.stream()
                .map(EntityColumn::getSQLValue)
                .toArray(String[]::new));

        String query = "INSERT INTO " + tableName + "(" + insertCol + ") values(" + insertVal + ")";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public <T> T select(String query, ResultHandler<T> handler){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return handler.handle(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


@FunctionalInterface
interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}

