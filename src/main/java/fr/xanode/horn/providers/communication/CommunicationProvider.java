package fr.xanode.horn.providers.communication;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.notification.NotificationState;

import java.util.List;

public interface CommunicationProvider {
    void sendNotification(Notification notification);
    Notification getLastNotification();
    List<Notification> getLastNotifications(int numbers);
    NotificationState getNotificationState(Notification notification);
}
