package by.dm13y.study.orm.dao.executor;

import by.dm13y.study.orm.dao.UsersDAO;
import by.dm13y.study.orm.entity.UserEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HW_Test {
    private final String connStr = "jdbc:postgresql://localhost:5432/hw_09";
    private final String user = "hw_09";
    private final String pwd = "hw_09";

    /**
     * create db: ./createDB.sh
     */
    @BeforeClass
    public static void createDB(){}

    @Test
    public void testHW() throws Exception{
        Connection connection = DriverManager.getConnection(connStr, user, pwd);
        connection.setAutoCommit(false);

        Executor executor = new Executor(connection);

        UsersDAO usersDAO = new UsersDAO(connection);
        Long userId = 3L;
        String test = "test";
        int age = 20;

        UserEntity user = usersDAO.getUser(userId);
        assertEquals(user.getId(), userId);
        assertEquals(user.getAge(), age);
        assertEquals(user.getName(), test);

        UserEntity user1 = new UserEntity();
        user1.setName("new");
        user1.setAge(255);
        usersDAO.saveUser(user1);

        Long savedId = executor.query("SELECT id FROM public.user WHERE id = " + user1.getId(), rs -> rs.next() ? rs.getLong("id"): null);
        String savedName = executor.query("SELECT name FROM public.user WHERE id = " + user1.getId(), rs -> rs.next() ? rs.getString("name"): null);
        assertEquals(savedId, user1.getId());
        assertEquals(savedName, user1.getName());

        String newName = "newTestName";
        user1.setName(newName);
        usersDAO.updateUser(user1);
        String savedNewName = executor.query("SELECT name FROM public.user WHERE id = " + user1.getId(), rs -> rs.next() ? rs.getString("name"): null);
        assertEquals(savedNewName, user1.getName());

        usersDAO.removeUser(user1);
        assertNull(executor.query("SELECT id FROM public.user WHERE id = " + user1.getId(), rs -> rs.next() ? rs.getLong("id"): null));
        connection.close();
    }

}
