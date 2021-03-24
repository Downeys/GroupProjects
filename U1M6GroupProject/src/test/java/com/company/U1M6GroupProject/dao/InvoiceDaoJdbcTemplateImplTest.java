package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void addInvoice() {
    }

    @Test
    public void getAllInvoices() {
    }

    @Test
    public void updateInvoice() {
    }

    @Test
    public void deleteInvoice() {
    }

    @Test
    public void getInvoicesByCustomer() {
    }
}