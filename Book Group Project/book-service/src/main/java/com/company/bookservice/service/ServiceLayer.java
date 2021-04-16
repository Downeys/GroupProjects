package com.company.bookservice.service;

import com.company.bookservice.dao.BookDao;
import com.company.bookservice.feign.NoteServiceFeignClient;
import com.company.bookservice.models.Book;
import com.company.bookservice.models.BookViewModel;
import com.company.bookservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    NoteServiceFeignClient client;
    BookDao dao;

    @Autowired
    public ServiceLayer(NoteServiceFeignClient client, BookDao dao){
        this.client = client;
        this.dao = dao;
    }

    public BookViewModel createBook(BookViewModel book){
        Book newBook = new Book();
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        return null;
    }

    public BookViewModel getBook(int bookId){
        Book book = dao.getBook(bookId);
        return buildBookViewModel(book);
    }

    public List<BookViewModel> getAllBooks(){
        List<Book> bookList = dao.getAllBooks();
        List<BookViewModel> returnVal = new ArrayList<>();
        for (Book b: bookList) {
            returnVal.add(buildBookViewModel(b));
        }
        return returnVal;
    }

    public void updateBook(BookViewModel book, int bookId){
        Book newBook = new Book();
        newBook.setBookId(book.getBookId());
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        
        //Todo update notes

        dao.updateBook(newBook);
    }

    public void deleteBook(int bookId){
        dao.deleteBook(bookId);
    }

    private BookViewModel buildBookViewModel(Book book){
        BookViewModel returnVal = new BookViewModel();
        returnVal.setBookId(book.getBookId());
        returnVal.setAuthor(book.getAuthor());
        returnVal.setTitle(book.getTitle());

        List<Note> notesByBook = client.getNotesByBookId(book.getBookId());
        List<String> notes = new ArrayList<>();
        for (Note n: notesByBook) {
            notes.add(n.getNote());
        }
        returnVal.setNotes(notes);

        return returnVal;
    }
}
