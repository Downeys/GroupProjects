package com.company.bookservice.controller;

import com.company.bookservice.models.Book;
import com.company.bookservice.models.BookViewModel;
import com.company.bookservice.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ServiceLayer serviceLayer;

    BookViewModel inputBook1, inputBook2, outputBook1, outputBook2;
    List<BookViewModel> allBooks;

    @Before
    public void setUp() throws Exception {
        setupTestBooks();
        setupMockServiceLayer();
    }

    @Test
    public void shouldCreateBookAndStatusCreated() throws Exception{
        String inputJsonForTest1 = mapper.writeValueAsString(inputBook1);
        String inputJsonForTest2 = mapper.writeValueAsString(inputBook2);

        String expectedOutputJsonForTest1 = mapper.writeValueAsString(outputBook1);
        String expectedOutputJsonForTest2 = mapper.writeValueAsString(outputBook2);

        mockMvc.perform(
                post("/books")
                        .content(inputJsonForTest1)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonForTest1));

        mockMvc.perform(
                post("/books")
                        .content(inputJsonForTest2)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonForTest2));

    }

    @Test
    public void shouldGetBookAndStatusOk() throws Exception{
        String test1 = "/books/1";
        String test2 = "/books/2";

        String expectedOutputJsonForTest1 = mapper.writeValueAsString(outputBook1);
        String expectedOutputJsonForTest2 = mapper.writeValueAsString(outputBook2);

        mockMvc.perform(
                get(test1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonForTest1));

        mockMvc.perform(
                get(test2)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonForTest2));

    }

    @Test
    public void shouldGetAllBooksAndReturnStatusOK() throws Exception{
        String expectedOutputJasonForTest = mapper.writeValueAsString(allBooks);

        mockMvc.perform(
                get("/books")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJasonForTest));
    }

    @Test
    public void shouldReturnOkStatusWhenUpdateIsPassedValidBook() throws Exception{
        String inputJsonForTest1 = mapper.writeValueAsString(outputBook1);
        String inputJsonForTest2 = mapper.writeValueAsString(outputBook2);

        mockMvc.perform(
                put("/books/1")
                        .content(inputJsonForTest1)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                put("/books/2")
                        .content(inputJsonForTest2)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void shouldReturnNoContentStatusWhenDeleteIsPassedValidId() throws Exception{
        String test1 = "/books/1";
        String test2 = "/books/2";

        mockMvc.perform(
                delete(test1)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(
                delete(test2)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    private void setupTestBooks(){
        inputBook1 = new BookViewModel();
        inputBook1.setAuthor("Fat Albert");
        inputBook1.setTitle("Fat Albert's Guide to Friendship");
        List<String> book1NoteList = new ArrayList<>();
        book1NoteList.add("This is the greatest book in the world.");
        inputBook1.setNotes(book1NoteList);

        inputBook2 = new BookViewModel();
        inputBook2.setAuthor("David Mitchell");
        inputBook2.setTitle("Cloud Atlas");
        List<String> book2NoteList = new ArrayList<>();
        book2NoteList.add("Generic Book Note");
        book2NoteList.add("This book is better than Fat Albert's book.");
        inputBook2.setNotes(book2NoteList);

        outputBook1 = new BookViewModel();
        outputBook1.setBookId(1);
        outputBook1.setAuthor("Fat Albert");
        outputBook1.setTitle("Fat Albert's Guide to Friendship");
        outputBook1.setNotes(book1NoteList);

        outputBook2 = new BookViewModel();
        outputBook2.setBookId(2);
        outputBook2.setAuthor("David Mitchell");
        outputBook2.setTitle("Cloud Atlas");
        outputBook2.setNotes(book2NoteList);

        allBooks = new ArrayList<>();
        allBooks.add(outputBook1);
        allBooks.add(outputBook2);
    }

    private void setupMockServiceLayer(){
        doReturn(outputBook1).when(serviceLayer).createBook(inputBook1);
        doReturn(outputBook2).when(serviceLayer).createBook(inputBook2);
        doReturn(outputBook1).when(serviceLayer).getBook(1);
        doReturn(outputBook2).when(serviceLayer).getBook(2);
//        doThrow(JdbcOperationFailedException.class).when(service).fetchTask(4);
//        doThrow(JdbcOperationFailedException.class).when(service).updateTask(taskWithInvalidId);
//        doThrow(JdbcOperationFailedException.class).when(service).deleteTask(6);
        doReturn(allBooks).when(serviceLayer).getAllBooks();
    }
}