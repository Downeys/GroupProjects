package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDaoJdbcTemplateImplTest {
    @Autowired
    private ItemDao itemDao;

    @Before
    public void setUp() throws Exception {

        List<Item> itemList = itemDao.getAllItems();
        for(Item i : itemList){
            itemDao.deleteItem(i.getItemId());
        }
    }

    @Test
    public void addItem() {
        //arrange
        Item item1 = new Item();
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));
        item1 = itemDao.addItem(item1);
        Item item2 = itemDao.getItem(item1.getItemId());
        List<Item> itemList = itemDao.getAllItems();

        //assert
        assertEquals(1,itemList.size());
        assertEquals(item1, item2);

        //act
        itemDao.deleteItem(item1.getItemId());
        itemList = itemDao.getAllItems();
        item2 = itemDao.getItem(item1.getItemId());
        //assert
        assertEquals(0, itemList.size());
        assertNull(item2);

    }

    @Test
    public void getAllItems() {
        //arrange
        Item item1 = new Item();
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));
        item1 = itemDao.addItem(item1);
        Item item2 = new Item();
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));
        item2 = itemDao.addItem(item2);
        //act
        List<Item> itemList = itemDao.getAllItems();
        //assert
        assertEquals(2,itemList.size());


    }

    @Test
    public void updateItem() {
        Item item1 = new Item();
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));
        item1 = itemDao.addItem(item1);
        Item updatedItem = itemDao.getItem(item1.getItemId());
        updatedItem.setName("Bed");
        updatedItem.setDescription("king size bed");
        updatedItem.setDailyRate(new BigDecimal("200.99"));
        //act
        itemDao.updateItem(updatedItem);
        Item returnItem = itemDao.getItem(item1.getItemId());
        //assert
        assertEquals(updatedItem, returnItem);
        assertNotEquals(item1,returnItem);
    }

    @Test
    public void deleteItem() {
        //arrange
        Item item1 = new Item();
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));
        item1 = itemDao.addItem(item1);
        Item item2 = new Item();
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));
        item2 = itemDao.addItem(item2);
        List<Item> itemList = itemDao.getAllItems();
        assertEquals(2, itemList.size());
        //act
        itemDao.deleteItem(item1.getItemId());
        itemList = itemDao.getAllItems();
        assertEquals(1, itemList.size());
        itemDao.deleteItem(item2.getItemId());
        itemList = itemDao.getAllItems();
        assertEquals(0, itemList.size());
    }
}