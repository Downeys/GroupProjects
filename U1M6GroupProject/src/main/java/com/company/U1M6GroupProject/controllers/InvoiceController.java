package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.InvoiceViewModel;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InvoiceController {

    private final ServiceLayer service;

    @Autowired
    public InvoiceController(ServiceLayer service){
        this.service = service;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel addItem(@RequestBody InvoiceViewModel invoice){
        return service.addInvoice(invoice);
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@RequestParam(required = false) Integer id,
                                           @RequestParam(required = false) Integer customerId){
        //Because the value and method parameters of the RequestMapping define the endpoint, we cannot
        //have two endpoints that have the value = "/invoice" and method = "RequestMethod.GET".
        //Therefore, we have to put both request parameters in the parameter list of the same endpoint.
        //We make each request parameter optional, so the user can send one or the other.

        InvoiceViewModel returnVal = new InvoiceViewModel();

        //Then we check to see which one they sent us.
        if(id != null){ //If they sent us an invoice ID then run this statement
            returnVal =  service.getInvoiceById(id);
        }

        if(customerId != null){ //If they sent us a customer ID then run this statement.
            returnVal =  service.getInvoiceByCustomerId(customerId);
        }

        return returnVal;
    }

}
