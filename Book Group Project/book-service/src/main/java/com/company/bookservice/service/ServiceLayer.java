package com.company.bookservice.service;

import com.company.bookservice.dao.BookDao;
import com.company.bookservice.feign.NoteServiceFeignClient;
import com.company.bookservice.models.Book;
import com.company.bookservice.models.BookViewModel;
import com.company.bookservice.models.Note;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    public static final String EXCHANGE = "queue-exchange";
    public static final String ROUTING_KEY = "note.add.book.controller";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    NoteServiceFeignClient client;
    @Autowired
    BookDao dao;

    public ServiceLayer(NoteServiceFeignClient client, BookDao dao){
        this.client = client;
        this.dao = dao;
    }

    public BookViewModel createBook(BookViewModel book){
        Book newBook = new Book();
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        newBook = dao.createBook(newBook);

        for (String n: book.getNotes()) {
            Note note = new Note();
            note.setNote(n);
            note.setBookId(newBook.getBookId());

            addNoteToQueue(note);

//            System.out.println("Sending message...");
//            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
//            System.out.println("Message Sent");

//            client.createNote(note);
        }


        return buildBookViewModel(newBook);
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


        updateNotes(book);

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

    public void updateNotes(BookViewModel book){
        List<Note> notesByBook = client.getNotesByBookId(book.getBookId());
        List<String> newNotes = book.getNotes();

        for (Note n: notesByBook) {
            client.deleteNote(n.getNoteId());
        }

        for(String s: newNotes){
            Note note = new Note();
            note.setBookId(book.getBookId());
            note.setNote(s);

            addNoteToQueue(note);

//            System.out.println("Sending message...");
//            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
//            System.out.println("Message Sent");

        }

    }

    public void addNoteToQueue(Note note){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
        System.out.println("Message Sent");
    }

}
