package by.dm13y.study.orm.dao;

import by.dm13y.study.orm.dao.executor.Executor;
import by.dm13y.study.orm.entity.UserEntity;
import java.sql.Connection;

public class UsersDAO {
    private final Executor executor;

    public UsersDAO(Connection connection){
        this.executor = new Executor(connection);
    }

    public UserEntity getUser(Long id){
        try {
            return executor.load(id, UserEntity.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateUser(UserEntity user) {
        try {
            executor.update(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void saveUser(UserEntity user){
        try {
            executor.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeUser(UserEntity user){
        try {
            executor.remove(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
