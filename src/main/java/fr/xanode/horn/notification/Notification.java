package fr.xanode.horn.notification;

import fr.xanode.horn.providers.flow.Item;

public class Notification {

    private final Item item;
    private NotificationState state;

    Notification(Item item) {
        this.item = item;
        this.state = NotificationState.UNINITIATED;
    }

    public String getSubject() {
        return this.item.getTitle();
    }

    public void setNotificationSate(NotificationState state) {
        this.state = state;
    }

    public NotificationState getNotificationState() {
        return this.state;
    }

    public String toPlainText() {
        return "[HORN SECURITY SERVICE] - " + item.getPublicationDate() + "\n"
        + item.getTitle() + "\n\n"
        + item.getDescription() + "\n\n"
        + item.getLink();
    }

    public String toMultipart() {
        return this.toPlainText(); // TODO: return mime/multipart data
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        return result;
    }

}
