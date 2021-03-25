package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
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
public class InvoiceDaoJdbcTemplateImplTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private InvoiceDao invoiceDao;
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
    }

    @Test
    public void shouldAddGetDeleteInvoice() {
        //arrange
        //Create objects to add to the database
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");
        customerDao.addCustomer(customer1);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        //Act
        invoice1 = invoiceDao.addInvoice(invoice1);
        List<Invoice> invoicesReturnedFromDatabase = invoiceDao.getAllInvoices();

        //Assert
        assertEquals(1, invoicesReturnedFromDatabase.size());

        //Act
        invoiceDao.deleteInvoice(invoice1.getInvoiceId());
        invoicesReturnedFromDatabase = invoiceDao.getAllInvoices();

        //Assert
        assertEquals(0, invoicesReturnedFromDatabase.size());

    }

    @Test
    public void getAllInvoices() {
        //arrange
        //Create objects to add to the database
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

        //Act
        List<Invoice> invoicesReceivedFromDatabase = invoiceDao.getAllInvoices();

        //Assert
        assertEquals(2, invoicesReceivedFromDatabase.size());

    }

    @Test
    public void updateInvoice() {
        //arrange
        //Create objects to add to the database
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");
        customer1 = customerDao.addCustomer(customer1);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(customer1.getCustomerId());
        invoice1.setLateFee(new BigDecimal("10.00"));
        invoice1.setOrderDate(LocalDate.of(2021, 2, 28));
        invoice1.setPickupDate(LocalDate.of(2021, 3, 15));
        invoice1.setReturnDate(LocalDate.of(2021, 3, 31));

        invoice1 = invoiceDao.addInvoice(invoice1);

        Invoice updatedInvoice = invoiceDao.getInvoice(invoice1.getInvoiceId());
        updatedInvoice.setReturnDate(LocalDate.of(2021, 4, 15));
        updatedInvoice.setLateFee(new BigDecimal("30.00"));

        //Act
        invoiceDao.updateInvoice(updatedInvoice);

        Invoice invoiceReturnedFromDatabase = invoiceDao.getInvoice(invoice1.getInvoiceId());

        //Assert
        assertEquals(updatedInvoice, invoiceReturnedFromDatabase);
        assertNotEquals(invoice1, invoiceReturnedFromDatabase);

    }

    @Test
    public void deleteInvoice() {
        //arrange
        //Create objects to add to the database
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
        List<Invoice> invoicesReturnedFromDatabase = invoiceDao.getAllInvoices();
        assertEquals(2, invoicesReturnedFromDatabase.size());

        //Act
        invoiceDao.deleteInvoice(invoice1.getInvoiceId());
        invoicesReturnedFromDatabase = invoiceDao.getAllInvoices();

        //Assert
        assertEquals(1, invoicesReturnedFromDatabase.size());

        //Act
        invoiceDao.deleteInvoice(invoice2.getInvoiceId());
        invoicesReturnedFromDatabase = invoiceDao.getAllInvoices();

        //Assert
        assertEquals(0, invoicesReturnedFromDatabase.size());

    }

    @Test
    public void getInvoicesByCustomer() {

        //arrange
        //Create objects to add to the database
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

        Invoice invoice3 = new Invoice();
        invoice3.setCustomerId(customer2.getCustomerId());
        invoice3.setLateFee(new BigDecimal("25.00"));
        invoice3.setOrderDate(LocalDate.of(2021, 1, 31));
        invoice3.setPickupDate(LocalDate.of(2021, 2, 15));
        invoice3.setReturnDate(LocalDate.of(2021, 2, 28));

        invoice1 = invoiceDao.addInvoice(invoice1);
        invoice2 = invoiceDao.addInvoice(invoice2);
        invoice3 = invoiceDao.addInvoice(invoice3);

        //Act
        List<Invoice> customer1InvoiceList = invoiceDao.getInvoicesByCustomer(customer1.getCustomerId());
        List<Invoice> customer2InvoiceList = invoiceDao.getInvoicesByCustomer(customer2.getCustomerId());

        //Assert
        assertEquals(1, customer1InvoiceList.size());
        assertEquals(customer1.getCustomerId(), customer1InvoiceList.get(0).getCustomerId());
        assertEquals(2, customer2InvoiceList.size());
        assertEquals(customer2.getCustomerId(), customer2InvoiceList.get(0).getCustomerId());
        assertEquals(customer2.getCustomerId(), customer2InvoiceList.get(1).getCustomerId());

    }
}