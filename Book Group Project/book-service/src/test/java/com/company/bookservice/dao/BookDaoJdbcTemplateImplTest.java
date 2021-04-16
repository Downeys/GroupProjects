package com.company.bookservice.dao;

import com.company.bookservice.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoJdbcTemplateImplTest {

    @Autowired
    BookDao dao;

    Book inputBook1, inputBook2, outputBook1, outputBook2;
    List<Book> allBooks;

    @Before
    public void setUp() throws Exception {
        List<Book> books = dao.getAllBooks();
        for (Book b: books) {
            dao.deleteBook(b.getBookId());
        }

        setUpTestBooks();
    }

    @Test
    public void shouldAddGetDeleteBook() {
        //Act
        inputBook1 = dao.createBook(inputBook1);

        Book bookThatWasGotten = dao.getBook(inputBook1.getBookId());

        //Assert
        assertEquals(inputBook1, bookThatWasGotten);

        //Act
        dao.deleteBook(inputBook1.getBookId());

        bookThatWasGotten = dao.getBook(inputBook1.getBookId());

        //Assert
        assertNull(bookThatWasGotten);
    }

    @Test
    public void shouldGetAllBooks() {
        inputBook1 = dao.createBook(inputBook1);

        //Act
        List<Book> allBooksInDatabase = dao.getAllBooks();

        //Assert
        assertEquals(1, allBooksInDatabase.size());

        inputBook2 = dao.createBook(inputBook2);

        //Act
        allBooksInDatabase = dao.getAllBooks();

        //Assert
        assertEquals(2, allBooksInDatabase.size());


        dao.deleteBook(inputBook1.getBookId());

        //Act
        allBooksInDatabase = dao.getAllBooks();

        //Assert
        assertEquals(1, allBooksInDatabase.size());
        assertEquals(inputBook2, allBooksInDatabase.get(0));
    }

    @Test
    public void updateBook() {
        Book expectedOutputFromTest = dao.createBook(inputBook1);
        expectedOutputFromTest.setTitle("Fat Albert's Guide to Love.");

        //Act
        dao.updateBook(expectedOutputFromTest);

        Book actualOutputFromTest = dao.getBook(inputBook1.getBookId());

        //Assert
        assertEquals(expectedOutputFromTest, actualOutputFromTest);
    }

    @Test
    public void deleteBook() {
        inputBook1 = dao.createBook(inputBook1);
        inputBook2 = dao.createBook(inputBook2);
        List<Book> bookList = dao.getAllBooks();
        assertEquals(2, bookList.size());


        //Act
        dao.deleteBook(inputBook2.getBookId());
        bookList = dao.getAllBooks();

        //Assert
        assertEquals(1, bookList.size());
        assertNotEquals(inputBook2, bookList.get(0));

        //Act
        dao.deleteBook(inputBook1.getBookId());
        bookList = dao.getAllBooks();

        //Assert
        assertEquals(0, bookList.size());
    }

    private void setUpTestBooks(){
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
}