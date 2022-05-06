package fr.xanode.horn.cli;

import fr.xanode.horn.providers.flow.FlowProvider;
import fr.xanode.horn.providers.flow.Item;
import fr.xanode.horn.providers.flow.rss.RssFlowProvider;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "check",
        description = "Interactive check for publicly disclosed vulnerabilities."
)
public class Check implements Callable<Integer> {
    @Option(names = {"-p", "--pattern"}, split = ",", required = true, description = "Find vulnerabilities matching one of those patterns (could be regex).")
    String[] patterns;

    @Option(names = {"-d", "--date"}, description = "Find vulnerabilities newer than specified date.")
    Date oldest;

    @Override
    public Integer call() {
        System.out.println("Getting RSS feed...");
        FlowProvider rssFlowProvider = new RssFlowProvider("https://www.cert.ssi.gouv.fr/feed/");
        ArrayList<Item> items = new ArrayList<>();
        for (String pattern : patterns) {
            items.addAll(rssFlowProvider.getItems(pattern));
        }
        for (Item item : items) {
            System.out.println(item + "\n");
        }
        System.out.println(items.size() + " items found matching " + Arrays.toString(patterns) + ".");
        // TODO: Use proper logging system
        // TODO: Browse every flow provider
        return 0;
    }
}
