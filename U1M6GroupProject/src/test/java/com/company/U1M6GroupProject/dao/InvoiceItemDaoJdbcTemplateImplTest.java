package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.models.InvoiceItem;
import com.company.U1M6GroupProject.models.Item;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoJdbcTemplateImplTest {
    @Autowired
    private InvoiceItemDao invoiceItemDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        for(Invoice i: invoiceList){
            invoiceDao.deleteInvoice(i.getInvoiceId());
        }
        List<Customer> customerList = customerDao.getAllCustomers();
        for (Customer c: customerList) {
            customerDao.deleteCustomer(c.getCustomerId());
        }
        List<Item> itemList = itemDao.getAllItems();
        for(Item i: itemList){
            itemDao.deleteItem(i.getItemId());
        }
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        for (InvoiceItem i: invoiceItemList) {
            invoiceItemDao.deleteInvoiceItem(i.getInvoiceItemId());
        }

    }

    @Test
    public void addInvoiceItem() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);
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
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));
        //act
        invoiceItem1  = invoiceItemDao.addInvoiceItem(invoiceItem1);

        List<InvoiceItem> returnedInvoiceItem = invoiceItemDao.getAllInvoiceItems();
        assertEquals(1,returnedInvoiceItem.size());
        invoiceItemDao.deleteInvoiceItem(invoiceItem1.getInvoiceItemId());
        returnedInvoiceItem = returnedInvoiceItem = invoiceItemDao.getAllInvoiceItems();
        assertEquals(0,returnedInvoiceItem.size());


    }

    @Test
    public void getAllInvoiceItems() {
        //arrange
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);
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
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));
        invoiceItemDao.addInvoiceItem(invoiceItem1);
        invoiceItemDao.addInvoiceItem(invoiceItem2);
        //act
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        //assert
        assertEquals(2, invoiceItemList.size());

    }

    @Test
    public void updateInvoiceItem() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);
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
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));
        invoiceItem1 = invoiceItemDao.addInvoiceItem(invoiceItem1);
        InvoiceItem updatedInvoiceItem = invoiceItemDao.getInvoiceItem(invoiceItem1.getInvoiceItemId());
        updatedInvoiceItem.setInvoiceId(invoice2.getInvoiceId());
        updatedInvoiceItem.setItemId(item2.getItemId());
        updatedInvoiceItem.setQuantity(3);
        //act
        invoiceItemDao.updateInvoiceItem(updatedInvoiceItem);
        InvoiceItem returnedInvoiceItem = invoiceItemDao.getInvoiceItem(invoiceItem1.getInvoiceItemId());
        //assert
        assertEquals(updatedInvoiceItem, returnedInvoiceItem);
        assertNotEquals(invoiceItem1, returnedInvoiceItem);
    }

    @Test
    public void deleteInvoiceItem() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);
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
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));
        invoiceItemDao.addInvoiceItem(invoiceItem1);
        invoiceItemDao.addInvoiceItem(invoiceItem2);
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        assertEquals(2, invoiceItemList.size());
        //act
        invoiceItemDao.deleteInvoiceItem(invoiceItem1.getInvoiceItemId());
        invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        assertEquals(1, invoiceItemList.size());
        invoiceItemDao.deleteInvoiceItem(invoiceItem2.getInvoiceItemId());
        invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        assertEquals(0, invoiceItemList.size());

    }
}