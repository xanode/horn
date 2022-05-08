package fr.xanode.horn.providers.communication;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.notification.NotificationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.PriorityBlockingQueue;

public class CommunicationProvider {

    private static Logger logger = LogManager.getLogger(CommunicationProvider.class);

    private final Properties properties;

    private PriorityBlockingQueue<Notification> notificationQueue;

    public CommunicationProvider(Properties properties) {
        this.properties = properties;
        this.notificationQueue = new PriorityBlockingQueue<>();
    }

    public void loadNotification(Notification notification) {
        notification.setNotificationSate(NotificationState.PENDING);
        this.notificationQueue.add(notification);
        logger.trace("Notification " + notification.hashCode() + " loaded.");
    }

    public void removeNotification(Notification notification) {
        if (notification.getNotificationState() != NotificationState.SENT) {
            logger.warn("Remove unsent notification " + notification.hashCode() + " !");
        } else {
            logger.info("Remove notification " + notification.hashCode() + ".");
        }
        this.notificationQueue.remove(notification);
    }

    public void sendNotification(Notification notification) {
        logger.info("Send notification : " + notification.hashCode());
        if (!this.notificationQueue.contains(notification)) {
            logger.error("Notification " + notification.hashCode() + " not loaded or already sent!");
            throw new IllegalArgumentException("Notification not loaded or already sent!");
        }
        if (notification.getNotificationState() == NotificationState.SENDING || notification.getNotificationState() == NotificationState.SENT) {
            logger.error("Notification " + notification.hashCode() + " already sent!");
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
        logger.info("Flush notification queue requested.");
        while (this.notificationQueue.peek() != null) {
            this.sendHeadNotification();
        }
    }

    public void sendHeadNotification() {
        logger.info("Send first pending notification.");
        if (this.notificationQueue.peek() == null) {
            logger.error("Trying to send first pending notification but the queue is empty!");
            throw new IllegalArgumentException("Empty queue!");
        }
        this.sendNotification(this.notificationQueue.peek());
    }

    public Properties getProperties() {
        return properties;
    }
}
