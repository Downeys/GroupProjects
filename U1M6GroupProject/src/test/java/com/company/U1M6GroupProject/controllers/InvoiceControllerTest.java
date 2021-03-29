package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.models.InvoiceItem;
import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private ServiceLayer service;

    @Before
    public void setUp(){
        setupServiceLayerMock();
    }

    @Test
    public void shouldAddItemAndReturnStatusCreated() throws Exception {
        //arrange
        //The controller should receive a customerViewModel from the user. That customerViewModel will not
        //yet have an id because it has not been added to the database yet. It will also not have a list of
        //invoices because this is a new customer who has not yet been invoiced.
        //arrange
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer1.setCustomerId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice1.setInvoiceId(2);
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        Item item1 = new Item();
        item1.setItemId(1);
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));

        Item item2 = new Item();
        item1.setItemId(2);
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem1);
        invoiceItemList.add(invoiceItem2);

        InvoiceViewModel inputForTest = new InvoiceViewModel();
        inputForTest.setCustomer(customer1);
        inputForTest.setInvoiceItems(invoiceItemList);
        inputForTest.setLateFee(new BigDecimal("10.00"));
        inputForTest.setOrderDate(LocalDate.of(2021, 2, 28));
        inputForTest.setPickupDate(LocalDate.of(2021, 3, 15));
        inputForTest.setReturnDate(LocalDate.of(2021, 3, 31));

        InvoiceViewModel expectedOutputForTest = new InvoiceViewModel();
        expectedOutputForTest.setInvoiceId(1);
        expectedOutputForTest.setCustomer(customer1);
        expectedOutputForTest.setInvoiceItems(invoiceItemList);
        expectedOutputForTest.setLateFee(new BigDecimal("10.00"));
        expectedOutputForTest.setOrderDate(LocalDate.of(2021, 2, 28));
        expectedOutputForTest.setPickupDate(LocalDate.of(2021, 3, 15));
        expectedOutputForTest.setReturnDate(LocalDate.of(2021, 3, 31));

        String inputJsonString = mapper.writeValueAsString(inputForTest);
        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                post("/invoice")
                        .content(inputJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetItemByIdAndReturnStatusOK() throws Exception{
        String uriForTest1 = "/invoice?id=1";
        //arrange
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer1.setCustomerId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice1.setInvoiceId(2);
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        Item item1 = new Item();
        item1.setItemId(1);
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));

        Item item2 = new Item();
        item1.setItemId(2);
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem1);
        invoiceItemList.add(invoiceItem2);

        InvoiceViewModel expectedOutputForTest = new InvoiceViewModel();
        expectedOutputForTest.setInvoiceId(1);
        expectedOutputForTest.setCustomer(customer1);
        expectedOutputForTest.setInvoiceItems(invoiceItemList);
        expectedOutputForTest.setLateFee(new BigDecimal("10.00"));
        expectedOutputForTest.setOrderDate(LocalDate.of(2021, 2, 28));
        expectedOutputForTest.setPickupDate(LocalDate.of(2021, 3, 15));
        expectedOutputForTest.setReturnDate(LocalDate.of(2021, 3, 31));

        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                get(uriForTest1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetInvoiceViewModelByCustomerIdAndReturnStatusOK() throws Exception{
        String uriForTest1 = "/invoice?customerId=2";
        //arrange
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer1.setCustomerId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice1.setInvoiceId(2);
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        Item item1 = new Item();
        item1.setItemId(1);
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));

        Item item2 = new Item();
        item1.setItemId(2);
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem1);
        invoiceItemList.add(invoiceItem2);

        InvoiceViewModel expectedOutputForTest = new InvoiceViewModel();
        expectedOutputForTest.setInvoiceId(2);
        expectedOutputForTest.setCustomer(customer2);
        expectedOutputForTest.setInvoiceItems(invoiceItemList);
        expectedOutputForTest.setLateFee(new BigDecimal("20.00"));
        expectedOutputForTest.setOrderDate(LocalDate.of(2021, 1, 15));
        expectedOutputForTest.setPickupDate(LocalDate.of(2021, 2, 1));
        expectedOutputForTest.setReturnDate(LocalDate.of(2021, 2, 15));

        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                get(uriForTest1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    private void setupServiceLayerMock(){
        //arrange
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        Customer customer2 = new Customer();
        customer1.setCustomerId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Cena");
        customer2.setEmail("jcena@gmail.com");
        customer2.setCompany("WWE");
        customer2.setPhone("8196543546");

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        Invoice invoice2 = new Invoice();
        invoice1.setInvoiceId(2);
        invoice2.setCustomerId(customer2.getCustomerId());
        invoice2.setLateFee(new BigDecimal("20.00"));
        invoice2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoice2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoice2.setReturnDate(LocalDate.of(2021, 2, 15));

        Item item1 = new Item();
        item1.setItemId(1);
        item1.setName("Lamp");
        item1.setDescription("silver lamp");
        item1.setDailyRate(new BigDecimal("1.05"));

        Item item2 = new Item();
        item1.setItemId(2);
        item2.setName("Desk");
        item2.setDescription("brown desk");
        item2.setDailyRate(new BigDecimal("99.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(invoice1.getInvoiceId());
        invoiceItem1.setItemId(item1.getItemId());
        invoiceItem1.setQuantity(32);
        invoiceItem1.setUnitRate(new BigDecimal("9.99"));
        invoiceItem1.setDiscount(new BigDecimal("1.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(invoice2.getInvoiceId());
        invoiceItem2.setItemId(item2.getItemId());
        invoiceItem2.setQuantity(25);
        invoiceItem2.setUnitRate(new BigDecimal("12.99"));
        invoiceItem2.setDiscount(new BigDecimal("2.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem1);
        invoiceItemList.add(invoiceItem2);

        InvoiceViewModel inputInvoiceViewModelForAddTest = new InvoiceViewModel();
        inputInvoiceViewModelForAddTest.setCustomer(customer1);
        inputInvoiceViewModelForAddTest.setInvoiceItems(invoiceItemList);
        inputInvoiceViewModelForAddTest.setLateFee(new BigDecimal("10.00"));
        inputInvoiceViewModelForAddTest.setOrderDate(LocalDate.of(2021, 2, 28));
        inputInvoiceViewModelForAddTest.setPickupDate(LocalDate.of(2021, 3, 15));
        inputInvoiceViewModelForAddTest.setReturnDate(LocalDate.of(2021, 3, 31));

        InvoiceViewModel invoiceViewModel1 = new InvoiceViewModel();
        invoiceViewModel1.setInvoiceId(1);
        invoiceViewModel1.setCustomer(customer1);
        invoiceViewModel1.setInvoiceItems(invoiceItemList);
        invoiceViewModel1.setLateFee(new BigDecimal("10.00"));
        invoiceViewModel1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoiceViewModel1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoiceViewModel1.setReturnDate(LocalDate.of(2021, 3, 31));

        InvoiceViewModel invoiceViewModel2 = new InvoiceViewModel();
        invoiceViewModel2.setInvoiceId(2);
        invoiceViewModel2.setCustomer(customer2);
        invoiceViewModel2.setInvoiceItems(invoiceItemList);
        invoiceViewModel2.setLateFee(new BigDecimal("20.00"));
        invoiceViewModel2.setOrderDate(LocalDate.of(2021, 1, 15));
        invoiceViewModel2.setPickupDate(LocalDate.of(2021, 2, 1));
        invoiceViewModel2.setReturnDate(LocalDate.of(2021, 2, 15));

        doReturn(invoiceViewModel1).when(service).addInvoice(inputInvoiceViewModelForAddTest);
        doReturn(invoiceViewModel1).when(service).getInvoiceById(1);
        doReturn(invoiceViewModel2).when(service).getInvoiceByCustomerId(2);

    }

}