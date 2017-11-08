package by.dm13y.study;

import org.junit.Test;
import java.io.File;
import java.util.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class HW_GCLogFactory {
    private final String JAVA_HOME = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    private final String CLASSPATH = System.getProperty("java.class.path") + ":"
            + this.getClass().getResource("").getPath().replace("test-classes/by/dm13y/study/", "classes");
    private final String CLASS_NAME = OutOfMemoryExecutor.class.getCanonicalName();
    @SuppressWarnings("FieldCanBeLocal")
    private final String MS = "-Xms128m";
    @SuppressWarnings("FieldCanBeLocal")
    private final String MX = "-Xmx128m";
//    private final String META_SPACE = "-XX:MaxMetaspace=32m";

    private void OutOfMemoryExecutor(List<String> customVmOption, String pathToLogFile) throws Exception{
        List<String> procParam = new ArrayList<>();
        Collections.addAll(procParam, JAVA_HOME, MS, MX);
        procParam.addAll(customVmOption);
        Collections.addAll(procParam, "-cp", CLASSPATH, CLASS_NAME);
        ProcessBuilder builder = new ProcessBuilder(procParam.toArray(new String[procParam.size()]));
        File log = new File(pathToLogFile);
        builder.redirectOutput(log);
        Process process = builder.start();
        process.waitFor();

    }

    /**
     * Serial GC - basic options(-XX:+UseSerialGC)
     *
     * -XX:MinHeapFreeRatio=n, -XX:MaxHeapFreeRatio=n
     * -XX:NewRatio=n
     * -XX:NewSize=n, -XX:MaxNewSize=n
     * -XX:SurvivorRatio=n
     * -XX:-UseGCOverheadLimit
     * -XX:+PrintTenuringDistribution
     */
    @Test
    public void test_SGC() throws Exception{
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseSerialGC"), "log/SGC_default.lg");
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseSerialGC", "-XX:MinHeapFreeRatio=35",
                "-XX:SurvivorRatio=6", "-XX:NewRatio=3"), "log/SGC_tuned.lg");
    }

    /**
     * PS Scavenge - basic options ("-XX:+UseParallelGC")
     *
     * @see #test_SGC()
     * -XX:ParallelGCThreads=n
     * -XX:-UseParallelOldGC
     * -XX:MaxGCPauseMillis=n
     * -XX:GCTimeRatio=n
     * -XX:YoungGenerationSizeIncrement=n
     * -XX:TenuredGenerationSizeIncrement=n
     * -XX:AdaptiveSizeDecrementScaleFactor=n
     * -XX:GenerationSizeIncrement=n
     */
    @Test
    public void test_PGC() throws Exception{
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseParallelGC"), "log/PGC_default.lg");
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseParallelGC",
                "-XX:ParallelGCThreads=6", "-XX:MaxGCPauseMillis=200"), "log/PGC_tuned.lg");
    }

    /**
     * ParNew - basic options ("-XX:+UseParNewGC")
     *
     * @see #test_PGC()
     */
    @Test
    public void test_PNewGC() throws Exception{
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseParNewGC"), "log/PNewGC_default.lg");
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseParNewGC",
                "-XX:ParallelGCThreads=6", "-XX:MaxGCPauseMillis=500"), "log/PNewGC_tuned.lg");
    }


    /**
     * CMS GC - basic option
     *
     * @see #test_PGC
     * -XX:+CMSIncrementalMode
     * Enables incremental mode.
     * default - disabled
     *
     * -XX:+CMSIncrementalPacing
     * Enables automatic pacing. The incremental mode duty cycle is automatically adjusted based on statistics collected while the JVM is running.
     * default - disabled
     *
     * -XX:CMSIncrementalDutyCycle=n
     * The percentage (0 to 100) of time between minor collections that the CMS collector is allowed to run.
     * If CMSIncrementalPacing is enabled, then this is just the initial value.
     * default - 10
     *
     * -XX:CMSIncrementalDutyCycleMin=n
     * The percentage (0 to 100) that is the lower bound on the duty cycle when CMSIncrementalPacing is enabled.
     * default - 0
     *
     * -XX:CMSIncrementalSafetyFactor=n
     * The percentage (0 to 100) used to add conservatism when computing the duty cycle
     * default - 10
     *
     * -XX:CMSIncrementalOffset=n
     * The percentage (0 to 100) by which the incremental mode duty cycle is shifted to the right within the period between minor collections.
     * default - 0
     *
     * -XX:CMSExpAvgFactor=n
     * The percentage (0 to 100) used to weight the current sample when computing exponential averages for the CMS collection statistics.
     * default - 25
     */
    @Test
    public void test_CMSGC() throws Exception{
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseConcMarkSweepGC"), "log/CMSGC_default.lg");
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseConcMarkSweepGC", "-XX:CMSIncrementalDutyCycle=50"), "log/CMSGC_tuned.lg");
    }



    /**
     * Garbage First (G1) Garbage Collection Options
     *
     * -XX:MaxGCPauseMillis=n
     * Sets a target for the maximum GC pause time. This is a soft goal, and the JVM will make its best effort to achieve it.
     *
     * -XX:InitiatingHeapOccupancyPercent=n
     * Percentage of the (entire) heap occupancy to start a concurrent GC cycle. It is used by GCs that trigger
     * a concurrent GC cycle based on the occupancy of the entire heap, not just one of the generations (e.g., G1).
     * A value of 0 denotes 'do constant GC cycles'. The default value is 45.
     *
     * -XX:NewRatio=n
     * Ratio of old/new generation sizes. The default value is 2.
     *
     * -XX:SurvivorRatio=n
     * Ratio of eden/survivor space size. The default value is 8.
     *
     * -XX:MaxTenuringThreshold=n
     * Maximum value for tenuring threshold. The default value is 15.
     *
     * -XX:ParallelGCThreads=n
     * Sets the number of threads used during parallel phases of the garbage collectors.
     * The default value varies with the platform on which the JVM is running.
     *
     * -XX:ConcGCThreads=n
     * Number of threads concurrent garbage collectors will use.
     * The default value varies with the platform on which the JVM is running.
     *
     * -XX:G1ReservePercent=n
     * Sets the amount of heap that is reserved as a false ceiling to reduce the possibility of promotion failure.
     * The default value is 10.
     *
     * -XX:G1HeapRegionSize=n
     * With G1 the Java heap is subdivided into uniformly sized regions. This sets the size of the individual sub-divisions.
     * The default value of this parameter is determined ergonomically based upon heap size.
     * The minimum value is 1Mb and the maximum value is 32Mb.
     */
    @Test
    public void test_GC1() throws Exception{
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseG1GC"), "log/G1GC_default.lg");
        OutOfMemoryExecutor(Arrays.asList("-XX:+UseG1GC", "-XX:MaxGCPauseMillis=500", "-XX:GCPauseIntervalMillis=1000"), "log/G1GC_tuned.lg");
    }
}
