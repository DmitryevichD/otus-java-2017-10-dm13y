package by.dm13y.study;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HW_GCReport {
    class GCInfo{
        final Map<String, String> params = new HashMap<>();

        GCInfo(String info){
            Arrays.stream(info.split(";")).forEach(
                    line -> {
                        String[] couples = line.split(":");
                        params.put(couples[0], couples[1]);
                    }
            );
        }

    }

    private void showLogInfo(Path path){
        List<GCInfo> info = new ArrayList<>();
        try {
            Files.lines(path).forEach(line -> info.add(new GCInfo(line)));
            System.out.println("log:" + path);

            info.stream()
                    .map(mp -> mp.params.get("end"))
                    .mapToLong(Long::parseLong)
                    .max().ifPresent(tm -> System.out.println("total time(ms):" + tm));
            System.out.println("-------------------------------------");
            HashSet<String> gcTypes = new HashSet<>();
            info.stream()
                    .map(gc -> gc.params.get("name"))
                    .forEach(gcTypes::add);

            for (String gcType : gcTypes) {
                System.out.print("GC:" + gcType);
                System.out.println("launches: " + info.stream()
                        .map(gc -> gc.params.get("name"))
                        .filter(gcType::equals)
                        .count());

                System.out.println(gcType + " total time(ms): " + info.stream()
                        .map(hmap -> hmap.params)
                        .filter(gc -> gcType.equals(gc.get("name")))
                        .mapToLong(gc -> Long.parseLong(gc.get("time")))
                        .sum());
                System.out.println("-------------------------------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("=================END=================\n\n");

    }

    @Test
    public void reportAboutGC() throws Exception{
        List<Path> logs = new ArrayList<>();
        Files.list(Paths.get("log")).forEach(logs::add);
        logs.forEach(this::showLogInfo);
    }
}
