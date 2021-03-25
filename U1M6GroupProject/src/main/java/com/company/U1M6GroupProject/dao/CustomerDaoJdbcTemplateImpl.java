package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao{

    private static final String INSERT_CUSTOMER_SQL =
            "insert into customer (first_name, last_name, email, company, phone) values (?, ?, ?, ?, ?)";
    private static final String SELECT_CUSTOMER_SQL =
            "select * from customer where customer_id = ?";
    private static final String SELECT_ALL_CUSTOMER_SQL =
            "select * from customer";
    private static final String UPDATE_CUSTOMER_SQL =
            "update customer set first_name = ?, last_name = ?, email = ?, company = ?, phone = ? " +
                    "where customer_id = ?";
    private static final String DELETE_CUSTOMER_SQL =
            "delete from customer where customer_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        //use Insert prepared statement to update database (add customer to database)
        jdbcTemplate.update(INSERT_CUSTOMER_SQL, customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getCompany(), customer.getPhone());
        //get the id that was assigned to the customer
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        //set the customer id
        customer.setCustomerId(id);
        //return completed object
        return customer;
    }

    @Override
    public Customer getCustomer(int id) {
        //queryForObject throws an exception, so we have to catch it
        try{
            //use Select prepared statement to retrieve customer from database by its id
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_SQL, this::mapRowToCustomer, id);
        }catch (EmptyResultDataAccessException e){
            //if there isn't a record in the table with that id, return null
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        //use Select_All prepared statement to retrieve all Customer tuples from table
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER_SQL, this::mapRowToCustomer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        //use Update prepared statement to update the customer tuple
        jdbcTemplate.update(UPDATE_CUSTOMER_SQL,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCompany(),
                customer.getPhone(),
                customer.getCustomerId());
    }

    @Override
    public void deleteCustomer(int id) {
        //use Delete prepared statement to delete customer tuple
        jdbcTemplate.update(DELETE_CUSTOMER_SQL, id);
    }

    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
        //create a customer object to parse the returned Json string
        Customer customer = new Customer();
        //Assign each value pair from the ResultSet to its corresponding Customer member value
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));
        customer.setCompany(rs.getString("company"));
        return customer;
    }
}
