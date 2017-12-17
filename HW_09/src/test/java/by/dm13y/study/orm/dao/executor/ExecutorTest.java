package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.orm.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ExecutorTest {
    private final String connStr = "jdbc:postgresql://localhost:5432/hw_09";
    private final String user = "hw_09";
    private final String pwd = "hw_09";
    private Executor executor;

    @Before
    public void init() throws Exception{
        Connection conn = DriverManager.getConnection(connStr, user, pwd);
        executor = new Executor(conn);
    }

    @Test
    public void execUpdate() throws Exception {
        UserEntity testEntity = new UserEntity(1, "test", 18);
        testEntity.setName("test-test");
        testEntity.setAge(24);
        executor.execUpdate(testEntity);

    }

}