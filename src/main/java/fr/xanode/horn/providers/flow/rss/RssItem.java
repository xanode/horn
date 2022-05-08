package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem implements Item {

    String author;
    Date publicationDate;
    String description;
    String guid;
    String link;
    String title;

    public RssItem(String author, String publicationDate, String description, String guid, String link, String title) throws ParseException {
        this.author = author;
        this.publicationDate = (new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH)).parse(publicationDate); // RFC 822
        this.description = description;
        this.guid = guid;
        this.link = link;
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Date getPublicationDate() {
        return this.publicationDate;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public String toString() {
        //return "RssItem [title=" + this.title + ", description=" + this.description + ", link=" + this.link + ", date=" + this.publicationDate + ", guid=" + this.guid + "]";
        return this.title + "\nDate: " + this.publicationDate + "\n" + this.description + "\n" + this.link;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.author == null) ? 0 : this.author.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.guid == null) ? 0 : this.guid.hashCode());
        result = prime * result + ((this.link == null) ? 0 : this.link.hashCode());
        result = prime * result + ((this.publicationDate == null) ? 0 : this.publicationDate.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        return result;
    }
}
