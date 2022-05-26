package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.Item;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class RssItem implements Item {

    String author;
    String description;
    String guid;
    String link;
    Date publicationDate;
    String title;

    public RssItem(String author, String publicationDate, String description, String guid, String link, String title) throws ParseException {
        this.author = author;
        this.publicationDate = (new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH)).parse(publicationDate); // RFC 822
        this.description = description;
        this.guid = guid;
        this.link = link;
        this.title = title;
    }
}
