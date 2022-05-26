package fr.xanode.horn.providers.flow.rss;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Objects;

public class RssParser {

    final URL url;

    public RssParser(String feedUrl) {
        try {
            url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + feedUrl);
        }
    }

    public RssFeed parse() {
        RssFeed feed = null;
        try {
            boolean isHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String publicationDate = "";
            String author = "";
            String guid = "";

            XMLInputFactory factory = XMLInputFactory.newInstance();
            InputStream in = url.openStream();
            XMLEventReader eventReader = factory.createXMLEventReader(in);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();
                    switch (localPart) {
                        case "title" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                title = event.asCharacters().getData();
                            }
                        }
                        case "description" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                description = event.asCharacters().getData();
                            }
                        }
                        case "link" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                link = event.asCharacters().getData();
                            }
                        }
                        case "language" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                language = event.asCharacters().getData();
                            }
                        }
                        case "copyright" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                copyright = event.asCharacters().getData();
                            }
                        }
                        case "pubDate" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                publicationDate = event.asCharacters().getData();
                            }
                        }
                        case "author" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                author = event.asCharacters().getData();
                            }
                        }
                        case "guid" -> {
                            event = eventReader.nextEvent();
                            if (event instanceof Characters) {
                                guid = event.asCharacters().getData();
                            }
                        }
                        case "item" -> {
                            if (isHeader) {
                                isHeader = false;
                                feed = new RssFeed(title, link, description, language, copyright, publicationDate);
                            }
                            event = eventReader.nextEvent();
                        }
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals("item")) {
                        RssItem item = new RssItem(author, publicationDate, description, guid, link, title);
                        Objects.requireNonNull(feed).addItem(item);
                        event = eventReader.nextEvent();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error parsing RSS feed: " + e.getMessage());
        } catch (XMLStreamException e) {
            throw new RuntimeException("Error parsing the XML file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing the publication date: " + e.getMessage());
        }
        return feed;
    }
}
