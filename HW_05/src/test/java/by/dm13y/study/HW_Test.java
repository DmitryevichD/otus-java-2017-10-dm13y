package by.dm13y.study;

import by.dm13y.study.annotations.After;
import by.dm13y.study.annotations.Before;
import by.dm13y.study.annotations.Test;

public class HW_Test {

    @Before
    public void before1(){
        System.out.println("Before1");
    }
    @Before
    public void before2(){
        System.out.println("Before2");
    }
    @Before
    public void before3(){
        System.out.println("Before3");
    }
    @Before
    public void before4(){
        System.out.println("Before4");
    }
    @Before
    public void before5(){
        System.out.println("Before5");
    }

    @Test
    public void test1(){
        System.out.println("Test1");
    }

    @Test
    public void test2(){
        System.out.println("Test2");
    }

    @Test
    public void test3(){
        System.out.println("Test3");
    }

    @After
    public void after1(){
        System.out.println("After1");
    }
    @After
    public void after2(){
        System.out.println("After2");
    }
    @After
    public void after3(){
        System.out.println("After3");
    }
    @After
    public void after4(){
        System.out.println("After4");
    }
    @After
    public void after5(){
        System.out.println("After5");
    }


    public static void main(String[] args) {
        TestExecutor.exec(HW_Test.class);
    }
}
