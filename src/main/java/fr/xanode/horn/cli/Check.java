package fr.xanode.horn.cli;

import fr.xanode.horn.providers.flow.FlowProvider;
import fr.xanode.horn.providers.flow.Item;
import fr.xanode.horn.providers.flow.rss.RssFlowProvider;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "check",
        description = "Interactive check for publicly disclosed vulnerabilities."
)
@Slf4j
public class Check implements Callable<Integer> {
    @Option(names = {"-p", "--pattern"}, split = ",", required = true, description = "Find vulnerabilities matching one of those patterns (could be regex).")
    String[] patterns;

    @Option(names = {"-d", "--date"}, split = ",", description = "Find vulnerabilities newer than specified date.")
    Date[] bounds;

    @Override
    public Integer call() {
        log.info("Getting RSS feed...");
        FlowProvider rssFlowProvider = new RssFlowProvider("https://www.cert.ssi.gouv.fr/feed/");
        ArrayList<Item> items = new ArrayList<>();
        log.info("Filtering by patterns... ");
        for (String pattern : patterns) {
            items.addAll(rssFlowProvider.getItems(pattern));
        }
        log.info("Items filtered.");
        log.info(items.size() + " items found matching " + Arrays.toString(patterns) + ".");
        if (bounds != null) {
            log.info("Filtering by date... ");
            items.retainAll(rssFlowProvider.getItems(bounds[0], bounds[1]));
            log.info("Items filtered.");
        }
        for (Item item : items) {
            System.out.println(item + "\n");
        }
        log.info(items.size() + " items found matching criteria.");
        // TODO: Browse every flow provider
        return 0;
    }
}
