package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;
import static by.dm13y.study.Assert.*;

public class HW_Test {
    private Integer result = null;
    @Before
    public void initTest(){
        result = 10;
    }

    @Test(expected = Exception.class)
    public void test1() throws Exception {
        throw new Exception("Test exception");
    }

    @Test
    public void test2(){
        assertTrue(5 + 5 == result);
    }

    @After
    public void destroyTest(){
        result = null;
    }


    public static void main(String[] args) throws Throwable {
        TestExecutor.exec("by.dm13y.study");
        TestExecutor.exec(HW_Test.class);
    }
}
