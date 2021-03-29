package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {

    ServiceLayer service;

    @Autowired
    public ItemController(ServiceLayer service){
        this.service = service;
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Item addItem(@RequestBody Item item){
        return service.addItem(item);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Item getItemById(@PathVariable int id){
        return service.getItemById(id);
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Item> getAllItems(){
        return service.getAllItems();
    }

    @RequestMapping(value = "/item", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateItem(@RequestBody @Valid Item item){
        service.updateItem(item);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id){
        service.deleteItem(id);
    }

}
