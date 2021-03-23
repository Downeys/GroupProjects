package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
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
public class CustomerDaoJdbcTemplateImplTest {

    @Autowired
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = customerDao.getAllCustomers();
        for (Customer c: customerList) {
            customerDao.deleteCustomer(c.getCustomerId());
        }
    }

    @Test
    public void addCustomer() {
    }

    @Test
    public void getAllCustomers() {
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void deleteCustomer() {
    }
}