package com.company.bookservice.controller;

import com.company.bookservice.models.Book;
import com.company.bookservice.models.BookViewModel;
import com.company.bookservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class BookController {

    ServiceLayer service;

    @Autowired
    public BookController(ServiceLayer service){
        this.service = service;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody BookViewModel book){
        return service.createBook(book);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel getBook(@PathVariable int id){
        return service.getBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<BookViewModel> getAllBooks(){
        return service.getAllBooks();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateBook(@RequestBody BookViewModel book, @PathVariable int id){
        service.updateBook(book, id);
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id){
        service.deleteBook(id);
    }

}
