package fr.xanode.horn.cli;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.providers.communication.mail.MailCommunicationProvider;
import fr.xanode.horn.providers.flow.Item;
import fr.xanode.horn.providers.flow.rss.RssFlowProvider;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Command(name = "daemon",
        description = "Manage Horn in daemon mode."
)
@Slf4j
public class Daemon implements Runnable {

    @Option(names = "start", description = "Start daemon")
    boolean start;

    @Option(names = "stop", description = "Stop daemon")
    boolean stop;

    @Option(names = "status", description = "Status of Horn daemon")
    boolean status;

    @Option(names = "--config", description = "Path to configuration file")
    String hornConfigPath;

    @Override
    public void run() {
        if (start) {
            log.info("Starting Horn daemon!");
            // Test mails
            if (hornConfigPath == null) {
                log.info("No configuration file provided: using default location.");
                hornConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "horn.properties";
            }
            try {
                log.info("Loading configuration file "+ hornConfigPath + "...");
                Properties hornProperties = new Properties();
                hornProperties.load(new FileInputStream(hornConfigPath));

                log.info("Loading mail communication provider...");
                MailCommunicationProvider mailCommunicationProvider = new MailCommunicationProvider(hornProperties);
                log.info("Get last RSS item from CERT-FR");
                RssFlowProvider rssFlowProvider = new RssFlowProvider("https://www.cert.ssi.gouv.fr/feed/");
                Item lastItem = rssFlowProvider.getLastItem();
                System.out.println(lastItem.getTitle());
                Notification notification = new Notification(lastItem);
                log.info("Sending notification");
                mailCommunicationProvider.loadNotification(notification);
                mailCommunicationProvider.flushNotificationQueue();
            } catch (IOException e) {
                log.error("Unable to load configuration file: " + e.getMessage());
            }
        }
        // TODO: do some stuff
    }
}
