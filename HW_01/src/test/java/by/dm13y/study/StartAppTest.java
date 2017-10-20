package by.dm13y.study;

import by.dm13y.study.StartApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class StartAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void startAppArgs() throws Exception {
        StartApp.main(new String[0]);
        assertTrue("Start app without args", outContent.toString().contains("usage"));
    }

    @Test
    public void startAppWithArg() throws Exception{
        String arg = "PrintThisArgs";
        StartApp.main(new String[]{"-a" + arg});
        assertTrue("Start app with args", outContent.toString().contains(arg));
    }

    @Test
    public void startAppWithHelp() throws Exception{
        StartApp.main(new String[]{"-h"});
        assertTrue("Start app with help arg", outContent.toString().contains("usage"));
    }

    @After
    public void cleanUpStream(){
        System.setOut(null);
    }

}