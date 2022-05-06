package fr.xanode.horn.providers.flow;

import java.util.Date;

public interface Item {
    String getTitle();
    Date getPublicationDate();
    String getDescription();
    String getLink();
    @Override
    String toString();
}
