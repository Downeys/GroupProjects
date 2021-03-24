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
    public void shouldAddGetDeleteCustomer() {
        //arrange
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");
        //act
        customer1 = customerDao.addCustomer(customer1);
        Customer customer2 = customerDao.getCustomer(customer1.getCustomerId());
        List<Customer> customerList = customerDao.getAllCustomers();

        //assert
        assertEquals(1,customerList.size());
        assertEquals(customer1, customer2);

        //act
        customerDao.deleteCustomer(customer1.getCustomerId());
        customerList = customerDao.getAllCustomers();
        customer2 = customerDao.getCustomer(customer1.getCustomerId());
        //assert
        assertEquals(0, customerList.size());
        assertNull(customer2);
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