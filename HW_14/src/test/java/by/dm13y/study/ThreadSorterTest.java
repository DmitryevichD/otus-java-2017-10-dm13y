package by.dm13y.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ThreadSorterTest {
    private final TestingData testData;

    public ThreadSorterTest(TestingData testData) {
        this.testData = testData;
    }

    @Test
    public void getCountWorkers() throws Exception {
        ThreadSorter<Integer> sorter = new ThreadSorter<>(testData.countThread);
        assertEquals(sorter.getCountWorkers(testData.arraySize), testData.countWorkers);
    }

    @Parameterized.Parameters
    public static List<TestingData> testData(){
        return Arrays.asList(
                new TestingData(100, 2, 1),
                new TestingData(1, 8, 1),
                new TestingData(10, 8, 1),
                new TestingData(10, 400, 6),
                new TestingData(6, 1880, 6)
        );
    }

    static class TestingData{
        public final int countThread;
        public final int arraySize;
        public final int countWorkers;

        public TestingData(int countThread, int arraySize, int countWorkers){
            this.countThread = countThread;
            this.countWorkers = countWorkers;
            this.arraySize = arraySize;
        }
    }

}