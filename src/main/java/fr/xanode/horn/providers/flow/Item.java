package fr.xanode.horn.providers.flow;

import java.util.Date;

public interface Item {

    String getAuthor();
    String getDescription();
    String getGuid();
    String getLink();
    Date getPublicationDate();
    String getTitle();

    @Override
    String toString();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
