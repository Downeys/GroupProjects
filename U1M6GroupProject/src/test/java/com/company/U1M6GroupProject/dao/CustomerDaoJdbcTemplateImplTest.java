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

        //Act
        List<Customer> customersReceivedFromDatabase = customerDao.getAllCustomers();

        //Assert
        assertEquals(2, customersReceivedFromDatabase.size());

    }

    @Test
    public void updateCustomer() {
        //arrange
        Customer customer1 = new Customer();
        customer1.setFirstName("Jerry");
        customer1.setLastName("Garcia");
        customer1.setEmail("jgarcia@gmail.com");
        customer1.setCompany("Grateful Dead");
        customer1.setPhone("2813846385");

        customer1 = customerDao.addCustomer(customer1);
        Customer updatedCustomer = customerDao.getCustomer(customer1.getCustomerId());
        updatedCustomer.setFirstName("Geddy");
        updatedCustomer.setLastName("Lee");
        updatedCustomer.setCompany("Rush");
        updatedCustomer.setPhone("45678932157");
        updatedCustomer.setEmail("geddy@rush.com");

        //Act
        customerDao.updateCustomer(updatedCustomer);
        Customer customerReceivedFromDatabase = customerDao.getCustomer(customer1.getCustomerId());

        //Assert
        assertEquals(updatedCustomer, customerReceivedFromDatabase);
        assertNotEquals(customer1, customerReceivedFromDatabase);

    }

    @Test
    public void deleteCustomer() {
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

        //add the created objects to the database
        customer1 = customerDao.addCustomer(customer1);
        customer2 = customerDao.addCustomer(customer2);

        //First get all of the customers from the database to confirm the original list size
        List<Customer> customersReceivedFromDatabase = customerDao.getAllCustomers();
        //confirms original listSize should be 2 because we added two customers
        assertEquals(2, customersReceivedFromDatabase.size());

        //Act
        //delete one customer from the database, get all customers from database again, and confirm new list size
        customerDao.deleteCustomer(customer1.getCustomerId());
        customersReceivedFromDatabase = customerDao.getAllCustomers();

        //Assert
        //list size should now equal 1 because we deleted a customer
        assertEquals(1, customersReceivedFromDatabase.size());

        //Act
        //delete the other customer and check the list size one more time
        customerDao.deleteCustomer(customer2.getCustomerId());
        customersReceivedFromDatabase = customerDao.getAllCustomers();

        //Assert
        //list size should now equal 0 because we deleted the last customer
        assertEquals(0, customersReceivedFromDatabase.size());

    }
}