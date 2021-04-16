package com.company.bookservice.service;

import com.company.bookservice.dao.BookDao;
import com.company.bookservice.feign.NoteServiceFeignClient;
import com.company.bookservice.models.Book;
import com.company.bookservice.models.BookViewModel;
import com.company.bookservice.models.Note;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    private BookDao dao;
    private NoteServiceFeignClient client;
    private ServiceLayer service;

    Book inputBook1, inputBook2, outputBook1, outputBook2;
    List<Book> allBooks;

    Note inputNote1, inputNote2, inputNote3, outputNote1, outputNote2, outputNote3;
    List<Note> allNotes, book1TestNotes, book2TestNotes;

    BookViewModel inputBookVM1, inputBookVM2, outputBookVM1, outputBookVM2;
    List<BookViewModel> allBookVMs;

    @Before
    public void setUp() throws Exception {
        setupTestNotes();
        setupTestBooks();
        setupTestBookViewModels();
        setupMockDao();
        setupMockFeignClient();
        service = new ServiceLayer(client, dao);
    }

    @Test
    public void createBook() {
        //Act
        BookViewModel actualOutputFromTest1 = service.createBook(inputBookVM1);
        BookViewModel actualOutputFromTest2 = service.createBook(inputBookVM2);

        //Assert
        assertEquals(outputBookVM1, actualOutputFromTest1);
        assertEquals(outputBookVM2, actualOutputFromTest2);

    }

    @Test
    public void getBook() {
        //Act
        BookViewModel actualOutputFromTest1 = service.getBook(1);
        BookViewModel actualOutputFromTest2 = service.getBook(2);

        //Assert
        assertEquals(outputBookVM1, actualOutputFromTest1);
        assertEquals(outputBookVM2, actualOutputFromTest2);
    }

    @Test
    public void getAllBooks() {
        //Act
        List<BookViewModel> actualListOfAllBooks = service.getAllBooks();

        //Assert
        assertEquals(2, actualListOfAllBooks.size());
        assertEquals(allBookVMs, actualListOfAllBooks);
    }

    private void setupMockDao(){
        dao = mock(BookDao.class);

        doReturn(outputBook1).when(dao).createBook(inputBook1);
        doReturn(outputBook2).when(dao).createBook(inputBook2);
        doReturn(outputBook1).when(dao).getBook(1);
        doReturn(outputBook2).when(dao).getBook(2);
//        doThrow(JdbcOperationFailedException.class).when(service).fetchTask(4);
//        doThrow(JdbcOperationFailedException.class).when(service).updateTask(taskWithInvalidId);
//        doThrow(JdbcOperationFailedException.class).when(service).deleteTask(6);
        doReturn(allBooks).when(dao).getAllBooks();
    }

    private void setupMockFeignClient(){
        client = mock(NoteServiceFeignClient.class);

        doReturn(outputNote1).when(client).createNote(inputNote1);
        doReturn(outputNote2).when(client).createNote(inputNote2);
        doReturn(outputNote3).when(client).createNote(inputNote3);
        doReturn(outputNote1).when(client).getNoteByNoteId(1);
        doReturn(outputNote2).when(client).getNoteByNoteId(2);
        doReturn(outputNote3).when(client).getNoteByNoteId(3);
//        doThrow(JdbcOperationFailedException.class).when(service).fetchTask(4);
//        doThrow(JdbcOperationFailedException.class).when(service).updateTask(taskWithInvalidId);
//        doThrow(JdbcOperationFailedException.class).when(service).deleteTask(6);
        doReturn(allNotes).when(client).getAllNotes();
        doReturn(book1TestNotes).when(client).getNotesByBookId(1);
        doReturn(book2TestNotes).when(client).getNotesByBookId(2);
    }

    private void setupTestBooks(){
        inputBook1 = new Book();
        inputBook1.setAuthor("Fat Albert");
        inputBook1.setTitle("Fat Albert's Guide to Friendship");

        inputBook2 = new Book();
        inputBook2.setAuthor("David Mitchell");
        inputBook2.setTitle("Cloud Atlas");

        outputBook1 = new Book();
        outputBook1.setBookId(1);
        outputBook1.setAuthor("Fat Albert");
        outputBook1.setTitle("Fat Albert's Guide to Friendship");

        outputBook2 = new Book();
        outputBook2.setBookId(2);
        outputBook2.setAuthor("David Mitchell");
        outputBook2.setTitle("Cloud Atlas");

        allBooks = new ArrayList<>();
        allBooks.add(outputBook1);
        allBooks.add(outputBook2);
    }

    public void setupTestBookViewModels(){
        inputBookVM1 = new BookViewModel();
        inputBookVM1.setAuthor("Fat Albert");
        inputBookVM1.setTitle("Fat Albert's Guide to Friendship");
        List<String> book1NoteList = new ArrayList<>();
        book1NoteList.add("This is the greatest book in the world.");
        inputBookVM1.setNotes(book1NoteList);

        inputBookVM2 = new BookViewModel();
        inputBookVM2.setAuthor("David Mitchell");
        inputBookVM2.setTitle("Cloud Atlas");
        List<String> book2NoteList = new ArrayList<>();
        book2NoteList.add("Generic Book Note");
        book2NoteList.add("This book is better than Fat Albert's book.");
        inputBookVM2.setNotes(book2NoteList);

        outputBookVM1 = new BookViewModel();
        outputBookVM1.setBookId(1);
        outputBookVM1.setAuthor("Fat Albert");
        outputBookVM1.setTitle("Fat Albert's Guide to Friendship");
        outputBookVM1.setNotes(book1NoteList);

        outputBookVM2 = new BookViewModel();
        outputBookVM2.setBookId(2);
        outputBookVM2.setAuthor("David Mitchell");
        outputBookVM2.setTitle("Cloud Atlas");
        outputBookVM2.setNotes(book2NoteList);

        allBookVMs = new ArrayList<>();
        allBookVMs.add(outputBookVM1);
        allBookVMs.add(outputBookVM2);
    }

    private void setupTestNotes(){
        inputNote1 = new Note();
        inputNote1.setBookId(1);
        inputNote1.setNote("This is a note.");

        inputNote2 = new Note();
        inputNote2.setBookId(2);
        inputNote2.setNote("This is another note.");

        inputNote3 = new Note();
        inputNote3.setBookId(1);
        inputNote3.setNote("Check yes or no.");

        outputNote1 = new Note();
        outputNote1.setNoteId(1);
        outputNote1.setBookId(1);
        outputNote1.setNote("This is a note.");

        outputNote2 = new Note();
        outputNote2.setNoteId(2);
        outputNote2.setBookId(2);
        outputNote2.setNote("This is another note.");

        outputNote3 = new Note();
        outputNote3.setNoteId(3);
        outputNote3.setBookId(1);
        outputNote3.setNote("Check yes or no.");

        allNotes = new ArrayList<>();
        allNotes.add(outputNote1);
        allNotes.add(outputNote2);
        allNotes.add(outputNote3);

        book1TestNotes = new ArrayList<>();
        book1TestNotes.add(outputNote1);
        book1TestNotes.add(outputNote3);

        book2TestNotes = new ArrayList<>();
        book2TestNotes.add(outputNote2);
    }
}