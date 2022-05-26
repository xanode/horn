package fr.xanode.horn.providers.communication;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.notification.NotificationState;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class CommunicationProvider {

    private final Properties properties;

    private PriorityBlockingQueue<Notification> notificationQueue;

    public CommunicationProvider(Properties properties) {
        this.properties = properties;
        this.notificationQueue = new PriorityBlockingQueue<>();
    }

    public void loadNotification(Notification notification) {
        notification.setNotificationSate(NotificationState.PENDING);
        notification.updateQueuingDate();
        this.notificationQueue.add(notification);
        log.trace("Notification " + notification.hashCode() + " loaded.");
    }

    public void removeNotification(Notification notification) {
        if (notification.getNotificationState() != NotificationState.SENT) {
            log.warn("Remove unsent notification " + notification.hashCode() + " from queue!");
        } else {
            log.info("Remove notification " + notification.hashCode() + " from queue.");
        }
        this.notificationQueue.remove(notification);
    }

    public void sendNotification(Notification notification) {
        log.info("Send notification " + notification.hashCode());
        if (!this.notificationQueue.contains(notification)) {
            log.error("Notification " + notification.hashCode() + " not loaded or already sent!");
            throw new IllegalArgumentException("Notification not loaded or already sent!");
        }
        if (notification.getNotificationState() == NotificationState.SENDING || notification.getNotificationState() == NotificationState.SENT) {
            log.error("Notification " + notification.hashCode() + " already sent!");
            throw new IllegalArgumentException("Notification already sent!");
        }
        // Logic implemented by inheritance
    }

    public Notification getHeadNotification() {
        return notificationQueue.peek();
    }

    public List<Notification> getLastNotifications(int numbers) {
        List<Notification> lastNotifications = new ArrayList<>();
        Iterator<Notification> notificationQueueIterator = this.notificationQueue.iterator();
        int i = 0;
        while (notificationQueueIterator.hasNext() && i < numbers) {
            lastNotifications.add(notificationQueueIterator.next());
        }
        return lastNotifications;
    }

    public void flushNotificationQueue() {
        log.info("Flush notification queue requested.");
        while (this.notificationQueue.peek() != null) {
            this.sendHeadNotification();
        }
    }

    public void sendHeadNotification() {
        log.info("Send first pending notification.");
        if (this.notificationQueue.peek() == null) {
            log.error("Trying to send first pending notification but the queue is empty!");
            throw new IllegalArgumentException("Empty queue!");
        }
        this.sendNotification(this.notificationQueue.peek());
    }

    public Properties getProperties() {
        return properties;
    }
}
