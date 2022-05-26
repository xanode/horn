package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.Item;
import lombok.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@Getter @Setter @ToString
public class RssFeed {

    public @NonNull String title;
    public @NonNull String link;
    public @NonNull String description;
    public @NonNull String language;
    public @NonNull String copyright;
    public @NonNull String publicationDate;

    ArrayList<Item> items = new ArrayList<>();

    public void addItem(RssItem item) {
        items.add(item);
    }
}
