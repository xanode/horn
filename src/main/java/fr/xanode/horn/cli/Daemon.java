package fr.xanode.horn.cli;

import fr.xanode.horn.notification.Notification;
import fr.xanode.horn.providers.communication.mail.MailCommunicationProvider;
import fr.xanode.horn.providers.flow.Item;
import fr.xanode.horn.providers.flow.rss.RssFlowProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Command(name = "daemon",
        description = "Manage Horn in daemon mode."
)
public class Daemon implements Runnable {

    // Create logger
    private static Logger logger = LogManager.getLogger(Daemon.class);

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
        // Update root log level
        Configurator.setRootLevel(Level.INFO);
        if (start) {
            logger.info("Starting Horn daemon!");
            // Test mails
            if (hornConfigPath == null) {
                logger.info("No configuration file provided: using default location.");
                String hornConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "horn.properties";
            }
            try {
                logger.info("Loading configuration file "+ hornConfigPath + "...");
                Properties hornProperties = new Properties();
                hornProperties.load(new FileInputStream(hornConfigPath));

                logger.info("Loading mail communcation provider...");
                MailCommunicationProvider mailCommunicationProvider = new MailCommunicationProvider(hornProperties);
                logger.info("Get last RSS item from CERT-FR");
                RssFlowProvider rssFlowProvider = new RssFlowProvider("https://www.cert.ssi.gouv.fr/feed/");
                Item lastItem = rssFlowProvider.getLastItem();
                System.out.println(lastItem.getTitle());
                Notification notification = new Notification(lastItem);
                logger.info("Sending notification");
                mailCommunicationProvider.loadNotification(notification);
                mailCommunicationProvider.flushNotificationQueue();
            } catch (IOException e) {
                logger.fatal("Unable to load configuration file: " + e.getMessage());
            }
        }
        // TODO: do some stuff
    }
}
