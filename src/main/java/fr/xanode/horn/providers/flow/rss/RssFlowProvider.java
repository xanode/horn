package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.FlowProvider;
import fr.xanode.horn.providers.flow.Item;

import java.util.ArrayList;
import java.util.Date;

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
        return this.feed.getItems().get(this.feed.getItems().size());
    }

    @Override
    public ArrayList<Item> getItems(Date oldest, Date newest) {
        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (oldest.before(item.getPublicationDate()) && newest.after(item.getPublicationDate())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    @Override
    public ArrayList<Item> getItems(String regex) {
        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item.getTitle().matches(regex) || item.getDescription().matches(regex)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
}
