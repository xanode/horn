package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.FlowProvider;
import fr.xanode.horn.providers.flow.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RssFlowProvider implements FlowProvider {

    RssFeed feed;

    public RssFlowProvider(String url) {
        this.feed = (new RssParser(url).parse());
    }

    @Override
    public ArrayList<Item> getItems() {
        return this.feed.getItems();
    }

    @Override
    public Item getLastItem() {
        return this.feed.getItems().get(0);
    }

    @Override
    public ArrayList<Item> getItems(Date oldest, Date newest) {
        Predicate<Item> before = item -> oldest.before(item.getPublicationDate());
        Predicate<Item> after = item -> newest.after(item.getPublicationDate());
        return (ArrayList<Item>) this.feed.items.stream().filter(before.and(after)).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Item> getItems(String regex) {
        Predicate<Item> title = item -> item.getTitle().matches(regex);
        Predicate<Item> description = item -> item.getDescription().matches(regex);
        return (ArrayList<Item>) this.feed.items.stream().filter(title.and(description)).collect(Collectors.toList());
    }
}
