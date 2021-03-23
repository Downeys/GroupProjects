package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;

import java.util.List;

public interface CustomerDao {

    Customer addCustomer(Customer customer);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);

}
