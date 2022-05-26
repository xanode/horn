package fr.xanode.horn.providers.communication.mail;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.notification.NotificationState;
import fr.xanode.horn.providers.communication.CommunicationProvider;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Slf4j
public class MailCommunicationProvider extends CommunicationProvider {

    public MailCommunicationProvider(Properties properties) {
        super(properties);
    }

    @Override
    public void sendNotification(Notification notification) {
        super.sendNotification(notification);
        // Setting notification state to sending
        notification.setState(NotificationState.SENDING);
        // Initiate connection
        String username = this.getProperties().getProperty("username");
        String password = this.getProperties().getProperty("password");
        Session session = Session.getInstance(this.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("horn@xanode.fr"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.getProperties().getProperty("recipient")));
            message.setSubject(notification.getSubject());

            MimeBodyPart mimeBodyPartWithMultipartContent = new MimeBodyPart();
            mimeBodyPartWithMultipartContent.setContent(notification.toMultipart(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPartWithMultipartContent);

            message.setContent(multipart);

            Transport.send(message);

            log.info("Notification " + notification.hashCode() + " successfully sent.");
            // Set notification state to sent and remove it from notification queue
            notification.setState(NotificationState.SENT);
            this.removeNotification(notification);
        } catch (MessagingException e) {
            notification.setState(NotificationState.FAILED);
            log.error("Failed to send notification " + notification.hashCode() + ": " + e.getMessage());
        }
    }

}
