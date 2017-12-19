package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.orm.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
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
        UserEntity userEntity = new UserEntity();
        userEntity.setName("test");
        userEntity.setAge(37);
        assertTrue(userEntity.getId() == null);
        executor.save(userEntity);
        assertTrue(userEntity.getId() > 0);
    }

    @Test
    public void remove() throws Exception{
        UserEntity userEntity = new UserEntity();
        userEntity.setName("removeTest");
        userEntity.setAge(37);
        executor.save(userEntity);
        Long id = userEntity.getId();

        Integer currentAge1 = executor.query("SELECT age FROM public.user WHERE id = " + id, rs -> rs.next() ? rs.getInt("age"): null);
        assertFalse(currentAge1 == null);

        executor.remove(userEntity);
        Integer currentAge2 = executor.query("SELECT age FROM public.user WHERE id = " + id, rs -> rs.next() ? rs.getInt("age"): null);
        assertTrue(currentAge2 == null);
    }

    @Test
    public void load() throws Exception{
        Long userId = 1L;
        UserEntity user = executor.load(userId, UserEntity.class);
        assertEquals(user.getId(), userId);
    }

    @Test
    public void update() throws Exception {
        UserEntity userEntity = executor.load(1L, UserEntity.class);
        String newName = userEntity.getName() + "upd_val";
        int newAge = userEntity.getAge() + 230;
        userEntity.setName(newName);
        userEntity.setAge(newAge);
        executor.update(userEntity);

        Integer currentAge = executor.query("SELECT age FROM public.user WHERE id = 1", rs -> rs.next() ? rs.getInt("age"): null);
        String currentName = executor.query("SELECT name FROM public.user WHERE id = 1", rs -> rs.next() ? rs.getString("name"): null);

        assertEquals(new Integer(newAge), currentAge);
        assertEquals(newName, currentName);
    }
}