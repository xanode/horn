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

    @Option(names = {"-d", "--date"}, split = ",", description = "Find vulnerabilities newer than specified date.")
    Date[] bounds;

    @Override
    public Integer call() {
        System.out.println("Getting RSS feed...");
        FlowProvider rssFlowProvider = new RssFlowProvider("https://www.cert.ssi.gouv.fr/feed/");
        ArrayList<Item> items = new ArrayList<>();
        System.out.print("Filtering by patterns... ");
        for (String pattern : patterns) {
            items.addAll(rssFlowProvider.getItems(pattern));
        }
        System.out.println("Done.");
        System.out.println(items.size() + " items found matching " + Arrays.toString(patterns) + ".");
        if (bounds != null) {
            System.out.print("Filtering by date... ");
            items.retainAll(rssFlowProvider.getItems(bounds[0], bounds[1]));
            System.out.println("Done.\n");
        }
        for (Item item : items) {
            System.out.println(item + "\n");
        }
        System.out.println("\n" + items.size() + " items found matching criteria.");
        // TODO: Use proper logging system
        // TODO: Browse every flow provider
        return 0;
    }
}
