package by.dm13y.study.orm.dao;

import by.dm13y.study.orm.dao.executor.Executor;
import by.dm13y.study.orm.entity.UserEntity;

import java.sql.Connection;

public class UsersDAO {
    private Connection connection;
    private Executor executor;

    public UsersDAO(Connection session){
        this.connection = session;
    }

    public UserEntity get(long id){
        throw new UnsupportedOperationException();
    }

    public void save(UserEntity user){

    }
}
