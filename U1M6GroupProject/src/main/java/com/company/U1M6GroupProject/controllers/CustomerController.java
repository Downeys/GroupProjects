package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    private final ServiceLayer service;

    @Autowired
    public CustomerController(ServiceLayer service){
        this.service = service;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerViewModel addCustomer(@RequestBody CustomerViewModel customer){
        return service.addCustomer(customer);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerViewModel getCustomerById(@PathVariable int id){
        return service.getCustomerById(id);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomerViewModels(){
        return service.getAllCustomers();
    }

    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCustomer(@RequestBody @Valid CustomerViewModel customer){
        service.updateCustomer(customer);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id){
        service.deleteCustomer(id);
    }
}
