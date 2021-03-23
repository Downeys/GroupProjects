package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Item;

import java.util.List;

public interface ItemDao {

    Item addItem(Item item);
    Item getItem(int id);
    List<Item> getAllItems();
    void updateItem(Item item);
    void deleteItem(int id);

}
