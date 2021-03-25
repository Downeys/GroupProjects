package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private static final String INSERT_INVOICE_SQL =
            "insert into invoice (customer_id, order_date, pickup_date, return_date, late_fee) values (?, ?, ?, ?, ?)";
    private static final String SELECT_INVOICE_SQL =
            "select * from invoice where invoice_id = ?";
    private static final String SELECT_ALL_INVOICES_SQL =
            "select * from invoice";
    private static final String UPDATE_INVOICE_SQL =
            "update invoice set customer_id = ?, order_date = ?, pickup_date = ?, return_date = ?, late_fee = ? " +
                    "where invoice_id = ?";
    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where invoice_id = ?";
    private static final String SELECT_INVOICE_BY_CUSTOMER_SQL =
            "select * from invoice where customer_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(INSERT_INVOICE_SQL, invoice.getCustomerId(), invoice.getOrderDate(), invoice.getPickupDate(),
                invoice.getReturnDate(), invoice.getLateFee());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    @Override
    public Invoice getInvoice(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_INVOICE_SQL, this::mapRowToInvoice, id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES_SQL, this::mapRowToInvoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(UPDATE_INVOICE_SQL, invoice.getCustomerId(), invoice.getOrderDate(), invoice.getPickupDate(),
                invoice.getReturnDate(), invoice.getLateFee(), invoice.getInvoiceId());
    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, id);
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return jdbcTemplate.query(SELECT_INVOICE_BY_CUSTOMER_SQL, this::mapRowToInvoice, customerId);
    }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setOrderDate(rs.getDate("order_date").toLocalDate());
        invoice.setPickupDate(rs.getDate("pickup_date").toLocalDate());
        invoice.setReturnDate(rs.getDate("return_date").toLocalDate());
        invoice.setLateFee(rs.getBigDecimal("late_fee"));
        return invoice;
    }


}
