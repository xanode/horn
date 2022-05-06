package fr.xanode.horn.notification;

import java.util.Date;

public class Notification {

    private String title;
    private String message;
    private String type;
    private String link;
    private Identity recipient;
    private Date sentDate;

    public Notification(String title, String message, String type, String link, Identity recipient) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.link = link;
        this.recipient = recipient;
    }

    public void send() {
        this.recipient.getCommunicator().notify(this);
        this.sentDate = new Date();
    }

    public boolean isSent() {
        return this.sentDate != null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Identity getRecipient() {
        return recipient;
    }

    public void setRecipient(Identity recipient) {
        this.recipient = recipient;
    }

    public Date getSentDate() {
        return sentDate;
    }
}
