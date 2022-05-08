package fr.xanode.horn.notification;

import fr.xanode.horn.providers.flow.Item;

public class Notification {

    private final Item item;
    private NotificationState state;

    Notification(Item item) {
        this.item = item;
        this.state = NotificationState.UNINITIATED;
    }

    void setNotificationSate(NotificationState state) {
        this.state = state;
    }

    NotificationState getNotificationState() {
        return this.state;
    }

    String toPlainText() {
        return "[HORN SECURITY SERVICE] - " + item.getPublicationDate() + "\n"
        + item.getTitle() + "\n\n"
        + item.getDescription() + "\n\n"
        + item.getLink();
    }

    String toMultipart() {
        return this.toPlainText(); // TODO: return mime/multipart data
    }

}
