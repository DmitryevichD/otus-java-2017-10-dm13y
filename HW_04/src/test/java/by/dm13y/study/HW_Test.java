package by.dm13y.study;

import org.junit.Before;
import org.junit.Test;
import org.omg.SendingContext.RunTime;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HW_Test {
    private final String JAVA_HOME = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    private final String CLASSPATH = System.getProperty("java.class.path") + ":"
            + this.getClass().getResource("").getPath().replace("test-classes/by/dm13y/study/", "classes");
    private final String CLASS_NAME = OutOfMemoryExecutor.class.getCanonicalName();
    private final String MS = "-Xms64m";
    private final String MX = "-Xmx64m";

    private void OutOfMemoryExecutor(String youngGC, String oldGC, String pathToLogFile) throws Exception{
        String[] params = new String[]{JAVA_HOME, MS, MX, youngGC, oldGC, "-cp", CLASSPATH, CLASS_NAME};
        ProcessBuilder builder = new ProcessBuilder(params);
        File log = new File(pathToLogFile);
        builder.redirectOutput(log);
        Process process = builder.start();
        process.waitFor();

    }


    @Test
    public void TestGC() throws Exception{
        OutOfMemoryExecutor("-XX:+UseSerialGC", "-XX:+UseSerialGC", "log/UseSerialGC_Test.log");

//        String javaHome = System.getProperty("java.home");
//        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
//        String classpath = System.getProperty("java.class.path");
//        String className = OutOfMemoryExecutor.class.getCanonicalName();
//        String path = ":" +OutOfMemoryExecutor.class.getResource("").getPath().replace("test-classes/by/dm13y/study/", "classes");
////        System.out.println();
//        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath + path, className, "> hom");
//
//        int i = 20;
//        File logFile = new File("11223344556677");
//        builder.redirectOutput(logFile);
//        Process process = builder.start();
////        OutputStream outputStream = process.getOutputStream();
////        System.out.println(outputStream.toString());
//        process.waitFor();



   //    Runtime.getRuntime().exec("ls -al > /home/dm13y/1111");
//        Runtime.getRuntime().exec("java -Xms64m -Xmx64m -XX:+UseParallelGC -XX:+UseParallelOldGC cp ../target/classes by.dm13y.study.main test");
    }
}
