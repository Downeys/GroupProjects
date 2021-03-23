package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(int id) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(int id) {

    }
}
