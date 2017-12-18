package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.orm.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertTrue;

public class ExecutorTest {
    private final String connStr = "jdbc:postgresql://localhost:5432/hw_09";
    private final String user = "hw_09";
    private final String pwd = "hw_09";
    private Executor executor;

    @Before
    public void init() throws Exception{
        Connection conn = DriverManager.getConnection(connStr, user, pwd);
        conn.setAutoCommit(false);
        executor = new Executor(conn);
    }

    @Test
    public void save() throws Exception{
        UserEntity userEntity = new UserEntity("test", 37);
        assertTrue(userEntity.getId() == null);
        executor.save(userEntity);
        assertTrue(userEntity.getId() > 0);
    }

    @Test
    public void update() throws Exception {
        throw new UnsupportedOperationException();
//        UserEntity testEntity = new UserEntity(1, "test", 18);
//        testEntity.setName("test-test");
//        testEntity.setAge(24);
//        executor.update(testEntity);

    }
}