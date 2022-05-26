package fr.xanode.horn.notification;

import fr.xanode.horn.providers.flow.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@EqualsAndHashCode
public class Notification implements Comparable<Notification> {

    private final Item item;
    @Getter @Setter private NotificationState state;
    private Date queuingDate;

    public Notification(Item item) {
        this.item = item;
        this.state = NotificationState.UNINITIATED;
        this.queuingDate = new Date();
    }

    public String getSubject() {
        return "[HORN SECURITY SERVICE] " + this.item.getTitle();
    }

    public void updateQueuingDate() {
        this.queuingDate = new Date();
    }

    public String toPlainText() {
        return "A new publicly disclosed cybersecurity vulnerability has been discovered.\n\n"
                + "Publication date: " + item.getPublicationDate() + "\n\n"
                + "Title: " + item.getTitle() + "\n\n"
                + "Description: " + item.getDescription() + "\n\n"
                + "Link: " + item.getLink();
    }

    public String toMultipart() {
        return "<h1>" + this.item.getTitle() + "</h1>\n"
                + "<p>" + this.item.getPublicationDate() + "</p><br />\n"
                + "<p>" + this.item.getDescription() + "</p><br />\n"
                + "<href a=\"" + this.item.getLink() + "\">Additional information here (" + this.item.getLink() +").</href>\n";
    }

    @Override
    public int compareTo(Notification notification) {
        return this.queuingDate.compareTo(notification.queuingDate);
    }
}
