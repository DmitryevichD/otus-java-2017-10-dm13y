package by.dm13y.study.utils;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.Date;

public class GcNotificationListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            System.out.println("act:" + info.getGcAction() + ";" +
                    "start:" + info.getGcInfo().getStartTime() + ";" +
                    "end:" + info.getGcInfo().getEndTime() + ";" +
                    "time:" + info.getGcInfo().getDuration());
        }
    }
}
