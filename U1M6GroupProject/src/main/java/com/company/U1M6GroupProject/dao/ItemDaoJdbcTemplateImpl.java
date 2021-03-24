package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoJdbcTemplateImpl implements ItemDao{

    @Override
    public Item addItem(Item item) {
        return null;
    }

    @Override
    public Item getItem(int id) {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return null;
    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(int id) {

    }
}
