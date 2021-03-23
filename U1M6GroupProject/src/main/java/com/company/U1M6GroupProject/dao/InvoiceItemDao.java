package com.company.U1M6GroupProject.dao;

import com.company.U1M6GroupProject.models.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem addInvoiceItem(InvoiceItem invoiceItem);
    InvoiceItem getInvoiceItem(int id);
    List<InvoiceItem> getAllInvoiceItems();
    void updateInvoiceItem(InvoiceItem invoiceItem);
    void deleteInvoiceItem(int id);

}
