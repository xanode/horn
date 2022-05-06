package fr.xanode.horn.providers.flow;

import java.util.ArrayList;
import java.util.Date;

public interface FlowProvider {
    ArrayList<Item> getItems();
    Item getLastItem();
    ArrayList<Item> getItems(Date oldest, Date newest);
    ArrayList<Item> getItems(String regex);
}
