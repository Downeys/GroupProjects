package com.company.U1M6GroupProject.service;

import com.company.U1M6GroupProject.dao.*;
import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.models.InvoiceItem;
import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    private ServiceLayer service;
    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;
    private ItemDao itemDao;

    @Before
    public void setUp(){
        setupMockCustomerDao();
        setupMockItemDao();
        setupMockInvoiceDao();
        setupMockInvoiceItemDao();

        service = new ServiceLayer(customerDao, invoiceDao, invoiceItemDao, itemDao);
    }

    @Test
    public void shouldSaveCustomer(){

        Invoice invoice1 = new Invoice();
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        List<Invoice> inputCustomerInvoiceList = new ArrayList<>();
        inputCustomerInvoiceList.add(invoice1);
        inputCustomerInvoiceList.add(invoice2);

        invoice1.setCustomerId(1);
        invoice1.setInvoiceId(1);
        invoice2.setCustomerId(1);
        invoice2.setInvoiceId(2);

        List<Invoice> outputCustomerInvoiceList = new ArrayList<>();
        outputCustomerInvoiceList.add(invoice1);
        outputCustomerInvoiceList.add(invoice2);

        CustomerViewModel inputAddTestCustomer = new CustomerViewModel();
        inputAddTestCustomer.setFirstName("Jerry");
        inputAddTestCustomer.setLastName("Garcia");
        inputAddTestCustomer.setEmail("jgarcia@gmail.com");
        inputAddTestCustomer.setCompany("Grateful Dead");
        inputAddTestCustomer.setPhone("2813846385");
        inputAddTestCustomer.setInvoices(inputCustomerInvoiceList);

        CustomerViewModel outputCustomer1 = new CustomerViewModel();
        outputCustomer1.setCustomerId(1);
        outputCustomer1.setFirstName("Jerry");
        outputCustomer1.setLastName("Garcia");
        outputCustomer1.setEmail("jgarcia@gmail.com");
        outputCustomer1.setCompany("Grateful Dead");
        outputCustomer1.setPhone("2813846385");
        outputCustomer1.setInvoices(outputCustomerInvoiceList);

        //Act
        CustomerViewModel returnedCustomerViewModel =  service.addCustomer(inputAddTestCustomer);

        //Assert
        assertEquals(outputCustomer1, returnedCustomerViewModel);
    }


    private void setupMockCustomerDao(){
        CustomerDao customerDaoMock = mock(CustomerDaoJdbcTemplateImpl.class);
        Customer inputCustomerForAddTest = new Customer();
        inputCustomerForAddTest.setFirstName("Jerry");
        inputCustomerForAddTest.setLastName("Garcia");
        inputCustomerForAddTest.setEmail("jgarcia@gmail.com");
        inputCustomerForAddTest.setCompany("Grateful Dead");
        inputCustomerForAddTest.setPhone("2813846385");

        Customer outputCustomer1 = new Customer();
        outputCustomer1.setCustomerId(1);
        outputCustomer1.setFirstName("Jerry");
        outputCustomer1.setLastName("Garcia");
        outputCustomer1.setEmail("jgarcia@gmail.com");
        outputCustomer1.setCompany("Grateful Dead");
        outputCustomer1.setPhone("2813846385");

        Customer outputCustomer2 = new Customer();
        outputCustomer2.setCustomerId(2);
        outputCustomer2.setFirstName("John");
        outputCustomer2.setLastName("Cena");
        outputCustomer2.setEmail("jcena@gmail.com");
        outputCustomer2.setCompany("WWE");
        outputCustomer2.setPhone("8196543546");

        List<Customer> outputCustomerList = new ArrayList<>();
        outputCustomerList.add(outputCustomer1);
        outputCustomerList.add(outputCustomer2);

        doReturn(outputCustomer1).when(customerDaoMock).addCustomer(inputCustomerForAddTest);
        doReturn(outputCustomer1).when(customerDaoMock).getCustomer(1);
        doReturn(outputCustomerList).when(customerDaoMock).getAllCustomers();
        customerDao = customerDaoMock;
    }

    private void setupMockItemDao(){
        //arrange
        ItemDao itemDaoMock = mock(ItemDaoJdbcTemplateImpl.class);

        Item inputItemForAddTest = new Item();
        inputItemForAddTest.setName("Lamp");
        inputItemForAddTest.setDescription("silver lamp");
        inputItemForAddTest.setDailyRate(new BigDecimal("1.05"));

        Item outputItem1 = new Item();
        outputItem1.setItemId(1);
        outputItem1.setName("Lamp");
        outputItem1.setDescription("silver lamp");
        outputItem1.setDailyRate(new BigDecimal("1.05"));

        Item outputItem2 = new Item();
        outputItem2.setItemId(2);
        outputItem2.setName("Desk");
        outputItem2.setDescription("brown desk");
        outputItem2.setDailyRate(new BigDecimal("99.99"));

        List<Item> outputItemList = new ArrayList<>();
        outputItemList.add(outputItem1);
        outputItemList.add(outputItem2);

        doReturn(outputItem1).when(itemDaoMock).addItem(inputItemForAddTest);
        doReturn(outputItem1).when(itemDaoMock).getItem(1);
        doReturn(outputItemList).when(itemDaoMock).getAllItems();
        itemDao = itemDaoMock;
    }

    private void setupMockInvoiceDao(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice inputInvoiceForAddTest1 = new Invoice();
        inputInvoiceForAddTest1.setLateFee(new BigDecimal("10.00"));
        inputInvoiceForAddTest1.setOrderDate(LocalDate.of(2021, 2, 28));
        inputInvoiceForAddTest1.setPickupDate(LocalDate.of(2021, 3, 15));
        inputInvoiceForAddTest1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice inputInvoiceForAddTest2 = new Invoice();
        inputInvoiceForAddTest2.setLateFee(new BigDecimal("20.00"));
        inputInvoiceForAddTest2.setOrderDate(LocalDate.of(2021, 1, 15));
        inputInvoiceForAddTest2.setPickupDate(LocalDate.of(2021, 2, 1));
        inputInvoiceForAddTest2.setReturnDate(LocalDate.of(2021, 2, 15));

        Invoice outputInvoice1 = new Invoice();
        outputInvoice1.setInvoiceId(1);
        outputInvoice1.setCustomerId(1);
        outputInvoice1.setLateFee(new BigDecimal("10.00"));
        outputInvoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        outputInvoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        outputInvoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice outputInvoice2 = new Invoice();
        outputInvoice2.setInvoiceId(2);
        outputInvoice2.setCustomerId(1);
        outputInvoice2.setLateFee(new BigDecimal("20.00"));
        outputInvoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        outputInvoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        outputInvoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        List<Invoice> outputInvoiceList = new ArrayList<>();
        outputInvoiceList.add(outputInvoice1);
        outputInvoiceList.add(outputInvoice2);

        doReturn(outputInvoice1).when(invoiceDao).addInvoice(inputInvoiceForAddTest1);
        doReturn(outputInvoice2).when(invoiceDao).addInvoice(inputInvoiceForAddTest2);
        doReturn(outputInvoice1).when(invoiceDao).getInvoice(1);
        doReturn(outputInvoiceList).when(invoiceDao).getAllInvoices();
        doReturn(outputInvoiceList).when(invoiceDao).getInvoicesByCustomer(1);

    }

    private void setupMockInvoiceItemDao(){

        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem inputInvoiceItemForAddTest = new InvoiceItem();
        inputInvoiceItemForAddTest.setInvoiceId(1);
        inputInvoiceItemForAddTest.setItemId(1);
        inputInvoiceItemForAddTest.setQuantity(32);
        inputInvoiceItemForAddTest.setUnitRate(new BigDecimal("9.99"));
        inputInvoiceItemForAddTest.setDiscount(new BigDecimal("1.99"));

        InvoiceItem outputInvoiceItem1 = new InvoiceItem();
        outputInvoiceItem1.setInvoiceItemId(1);
        outputInvoiceItem1.setInvoiceId(1);
        outputInvoiceItem1.setItemId(1);
        outputInvoiceItem1.setQuantity(32);
        outputInvoiceItem1.setUnitRate(new BigDecimal("9.99"));
        outputInvoiceItem1.setDiscount(new BigDecimal("1.99"));

        InvoiceItem outputInvoiceItem2 = new InvoiceItem();
        outputInvoiceItem2.setInvoiceItemId(2);
        outputInvoiceItem2.setInvoiceId(2);
        outputInvoiceItem2.setItemId(2);
        outputInvoiceItem2.setQuantity(25);
        outputInvoiceItem2.setUnitRate(new BigDecimal("12.99"));
        outputInvoiceItem2.setDiscount(new BigDecimal("2.99"));

        List<InvoiceItem> outputInvoiceItemList = new ArrayList<>();
        outputInvoiceItemList.add(outputInvoiceItem1);
        outputInvoiceItemList.add(outputInvoiceItem2);

        doReturn(outputInvoiceItem1).when(invoiceItemDao).addInvoiceItem(inputInvoiceItemForAddTest);
        doReturn(outputInvoiceItem1).when(invoiceItemDao).getInvoiceItem(1);
        doReturn(outputInvoiceItemList).when(invoiceItemDao).getAllInvoiceItems();
    }

}