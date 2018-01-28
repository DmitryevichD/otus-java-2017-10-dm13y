package by.dm13y.study.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheExecutorTest {
    @Test
    public void RunExecutor() throws Exception {
        new CacheExecutor().start();
    }

}