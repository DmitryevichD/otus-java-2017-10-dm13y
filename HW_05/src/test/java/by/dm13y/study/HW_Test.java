package by.dm13y.study;

import by.dm13y.study.annotations.Test;

public class HW_Test {
    @Test
    public void testMsg(){
        System.out.println("STARTED");
    }

    public static void main(String[] args) {
        TestExecutor.run(HW_Test.class);
        new HW_Test().testMsg();
    }
}
