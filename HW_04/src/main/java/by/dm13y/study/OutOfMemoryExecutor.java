package by.dm13y.study;

import by.dm13y.study.utils.GcNotificationListener;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutOfMemoryExecutor {
    private static void installGCMonitoring(){
        GcNotificationListener listener = new GcNotificationListener();

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static void outOfMemory() throws Exception{
        int size = 1_000_000;
        List<Object> array = new ArrayList<>();
        while (true) {
            for (int i = 0; i < size; i++) {
                array.add(new String("" + i));
            }
            for (int i = size - 1; i>=0; i--) {
                if (i % 4 == 0) {
                    array.remove(i);
                }
            }
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) throws Exception{
        installGCMonitoring();
        System.out.println("start:" + new Date().getTime());
        outOfMemory();
    }
}
