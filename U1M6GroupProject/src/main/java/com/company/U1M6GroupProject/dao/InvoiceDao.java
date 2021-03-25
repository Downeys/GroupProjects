package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice addInvoice(Invoice invoice);
    Invoice getInvoice(int id);
    List<Invoice> getAllInvoices();
    void updateInvoice(Invoice invoice);
    void deleteInvoice(int id);
    List<Invoice> getInvoicesByCustomer(int customerId);

}
