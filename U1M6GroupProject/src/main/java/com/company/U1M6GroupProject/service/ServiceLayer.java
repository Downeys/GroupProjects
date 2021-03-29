package com.company.U1M6GroupProject.service;

import com.company.U1M6GroupProject.dao.CustomerDao;
import com.company.U1M6GroupProject.dao.InvoiceDao;
import com.company.U1M6GroupProject.dao.InvoiceItemDao;
import com.company.U1M6GroupProject.dao.ItemDao;
import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import com.company.U1M6GroupProject.viewModels.InvoiceViewModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceLayer {

    private CustomerDao customerDao;
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;
    private ItemDao itemDao;

    public ServiceLayer(CustomerDao customerDao, InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao,
                        ItemDao itemDao) {
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
        this.itemDao = itemDao;
    }

    @Transactional
    public CustomerViewModel addCustomer(CustomerViewModel customer) {
        Customer c = new Customer();
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setCompany(customer.getCompany());
        c.setPhone(customer.getPhone());
        c.setEmail(customer.getEmail());
        c = customerDao.addCustomer(c);
        customer.setCustomerId(c.getCustomerId());

        List<Invoice> customerInvoiceList = customer.getInvoices();

        for (Invoice i : customerInvoiceList) {
            i.setCustomerId(customer.getCustomerId());
            i = invoiceDao.addInvoice(i);
        }

//        customerInvoiceList.stream()
//                .forEach(invoice -> {
//                    invoice.setCustomerId(customer.getCustomerId());
//                    invoice = invoiceDao.addInvoice(invoice);
//                });

        customerInvoiceList = invoiceDao.getInvoicesByCustomer(customer.getCustomerId());

        customer.setInvoices(customerInvoiceList);

        return customer;
    }

    public CustomerViewModel getCustomerById(int id) {
        return null;
    }

    public List<CustomerViewModel> getAllCustomers() {
        return null;
    }

    @Transactional
    public void updateCustomer(CustomerViewModel customer) {

    }

    @Transactional
    public void deleteCustomer(int id) {

    }

    @Transactional
    public Item addItem(Item item) {
        return null;
    }

    public Item getItemById(int id) {
        return null;
    }

    public List<Item> getAllItems() {
        return null;
    }

    @Transactional
    public void updateItem(Item item) {

    }

    @Transactional
    public void deleteItem(int id) {

    }

    @Transactional
    public InvoiceViewModel addInvoice(InvoiceViewModel invoice) {
        return null;
    }

    public InvoiceViewModel getInvoiceById(int id) {
        return null;
    }

    public InvoiceViewModel getInvoiceByCustomerId(int customerId) {
        return null;
    }

}
