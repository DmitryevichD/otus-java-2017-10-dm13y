package by.dm13y.study;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multiset;
import lombok.Getter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.DoubleToIntFunction;

public class HW_GCReport {
    class GCEvent {
        @Getter private String gcName;
        @Getter private String cause;
        @Getter private String act;
        @Getter private Long start;
        @Getter private Long end;
        @Getter private Long time;

        GCEvent(String info){
            Arrays.stream(info.split(";"))
                        .forEach(line -> {
                            String[] couples = line.split(":");
                            String param = couples[0];
                            if(param.equals("name")) gcName = couples[1];
                            if(param.equals("cause")) cause = couples[1];
                            if(param.equals("act")) act = couples[1];
                            if(param.equals("start")) start = Long.parseLong(couples[1]);
                            if(param.equals("end")) end = Long.parseLong(couples[1]);
                            if(param.equals("start")) start = Long.parseLong(couples[1]);
                            if(param.equals("time")) time = Long.parseLong(couples[1]);
                        });
        }
    }

    private long getTotalTimeApp(List<GCEvent> events){
        return events.stream()
                .map(info -> info.end)
                .mapToLong(Long::new)
                .max().getAsLong();
    }

    private Map<String, List<GCEvent>> getDetailGCInfo(List<GCEvent> info) {
        Map<String, List<GCEvent>> gcEvents = new HashMap<>();
        for (GCEvent gcEvent : info) {
            if (gcEvents.get(gcEvent.gcName) == null) {
                gcEvents.put(gcEvent.gcName, new ArrayList<>());
            }
            gcEvents.get(gcEvent.gcName).add(gcEvent);
        }
        return gcEvents;
    }

    private List<GCEvent> parseToGCInfo(Path path) {
        List<GCEvent> info = new ArrayList<>();
        try {
            Files.lines(path).forEach(line -> info.add(new GCEvent(line)));
        } catch (IOException ex) {}
        return info;
    }

    private Long getTotalGcTime(Path pathToLog){
        List<GCEvent> info = parseToGCInfo(pathToLog);
        return info.stream()
                .mapToLong(event -> event.time)
                .sum();
    }

    private void showLogInfo(Path pathToLog){
        List<GCEvent> info = parseToGCInfo(pathToLog);

        System.out.println("log:" + pathToLog);

        System.out.println("total time(ms):" + getTotalTimeApp(info));
        System.out.println("-------------------------------------");

        for (Map.Entry<String, List<GCEvent>> gcEntry : getDetailGCInfo(info).entrySet()) {
            System.out.print("GC:" + gcEntry.getKey());
            long totalTime = gcEntry.getValue().stream()
                    .mapToLong(e -> e.time)
                    .sum();

            System.out.println("total time(ms): " + totalTime);
            System.out.println("launches: " + gcEntry.getValue().size());
            System.out.println("-------------------------------------");
        }

        System.out.println("=================END=================\n\n");
    }

    private List<Path> getFileLogs() throws Exception{
        List<Path> logs = new ArrayList<>();
        Files.list(Paths.get("log")).forEach(logs::add);
        return logs;
    }

    @Test
    public void reportAboutGC() throws Exception{
        getFileLogs().forEach(this::showLogInfo);
    }

    @Test
    public void ratingAppTime() throws Exception{
        SortedMap<Long, String> timeOrder = new TreeMap<>();
        getFileLogs().forEach(name -> {
            long totalTime = getTotalTimeApp(parseToGCInfo(name));
            long onlyApp = totalTime - getTotalGcTime(name);
            timeOrder.put(onlyApp, name.toString());});

        System.out.println("sort by the app time");
        for (Map.Entry<Long, String> e : timeOrder.entrySet()) {
            System.out.println("name:" + e.getValue() + " time:" + e.getKey());
        }
        System.out.println("=================END=================\n\n");
    }

    @Test
    public void ratingGCTime() throws Exception{
        SortedMap<Long, String> timeOrder = new TreeMap<>();
        getFileLogs().forEach(
                name -> timeOrder.put(getTotalGcTime(name), name.toString())
        );

        System.out.println("sort by the gc time");
        for (Map.Entry<Long, String> e : timeOrder.entrySet()) {
            System.out.println("name:" + e.getValue() + " time:" + e.getKey());
        }
        System.out.println("=================END=================\n\n");
    }

    @Test
    public void ratingNumberOfCalls() throws Exception{
        SortedMap<Long, String> timeOrder = new TreeMap<>();
        getFileLogs().forEach(
                name -> timeOrder.put((long)parseToGCInfo(name).size(), name.toString())
        );

        System.out.println("sort by numberOfCalls");
        for (Map.Entry<Long, String> e : timeOrder.entrySet()) {
            System.out.println("name:" + e.getValue() + " count:" + e.getKey());
        }
        System.out.println("=================END=================\n\n");
    }

    @Test
    public void ratingAverageDelay() throws Exception{
        SortedMap<Double, String> timeOrder = new TreeMap<>();
        getFileLogs().forEach(

                name -> timeOrder.put(parseToGCInfo(name)
                        .stream()
                        .mapToLong(e -> e.time)
                        .average().getAsDouble(), name.toString())
        );

        System.out.println("sort by average time");
        for (Map.Entry<Double, String> e : timeOrder.entrySet()) {
            System.out.println("name:" + e.getValue() + " average:" + e.getKey());
        }
        System.out.println("=================END=================\n\n");
    }

}
