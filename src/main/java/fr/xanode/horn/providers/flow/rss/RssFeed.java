package fr.xanode.horn.providers.flow.rss;

import fr.xanode.horn.providers.flow.Item;

import java.util.ArrayList;

public class RssFeed {

    public String title;
    public String link;
    public String description;
    public String language;
    public String copyright;
    public String publicationDate;

    ArrayList<Item> items;

    public RssFeed(String title, String link, String description, String language, String copyright, String publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.publicationDate = publicationDate;
        items = new ArrayList<Item>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(RssItem item) {
        items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "RssFeed [title=" + title + ", link=" + link + ", description=" + description + ", language=" + language + ", copyright=" + copyright + ", publicationDate=" + publicationDate + ", items=" + items + "]";
    }
}
