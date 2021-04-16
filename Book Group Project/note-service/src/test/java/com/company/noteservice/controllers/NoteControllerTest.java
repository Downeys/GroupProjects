package com.company.noteservice.controllers;

import com.company.noteservice.dao.NoteDao;
import com.company.noteservice.dao.NoteDaoJdbcTemplateImpl;
import com.company.noteservice.models.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    NoteDao dao;

    Note testNote1, testNote2, testNote3, testNoteOutput1, testNoteOutput2, testNoteOutput3;

    List<Note> allTestNotes, book1TestNotes, book2TestNotes;

    @Before
    public void setUp() throws Exception {
        setUpMockDao();
        setUpTestNotes();
    }

    @Test
    public void shouldCreateNoteAndReturnStatusCreated() throws Exception{
        String inputJsonForTest1 = mapper.writeValueAsString(testNote1);
        String inputJsonForTest2 = mapper.writeValueAsString(testNote2);
        String inputJsonForTest3 = mapper.writeValueAsString(testNote3);

        String expectedOutputJsonForTest1 = mapper.writeValueAsString(testNoteOutput1);
        String expectedOutputJsonForTest2 = mapper.writeValueAsString(testNoteOutput2);
        String expectedOutputJsonForTest3 = mapper.writeValueAsString(testNoteOutput3);

        mockMvc.perform(
                post("/notes")
                .content(inputJsonForTest1)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonForTest1));

        mockMvc.perform(
                post("/notes")
                        .content(inputJsonForTest2)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonForTest2));

        mockMvc.perform(
                post("/notes")
                        .content(inputJsonForTest3)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonForTest3));
    }

    @Test
    public void shouldGetNoteByNoteIdAndStatusOK() throws Exception{
        String test1 = "/notes/1";
        String test2 = "/notes/2";
        String test3 = "/notes/3";

        String expectedOutputJsonForTest1 = mapper.writeValueAsString(testNoteOutput1);
        String expectedOutputJsonForTest2 = mapper.writeValueAsString(testNoteOutput2);
        String expectedOutputJsonForTest3 = mapper.writeValueAsString(testNoteOutput3);

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

        mockMvc.perform(
                get(test3)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonForTest3));
    }

    @Test
    public void shouldGetNotesByBookIdAndStatusOK() throws Exception{
        String test1 = "/notes/book/1";
        String test2 = "/notes/book/2";

        String expectedOutputJsonForTest1 = mapper.writeValueAsString(book1TestNotes);
        String expectedOutputJsonForTest2 = mapper.writeValueAsString(book2TestNotes);

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
    public void ShouldGetAllNotesAndStatusOK() throws Exception{
        String expectedOutputJasonForTest = mapper.writeValueAsString(allTestNotes);

        mockMvc.perform(
                get("/notes")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJasonForTest));
    }

    @Test
    public void shouldReturnStatusOKWhenUpdateIsPassedAValidNote() throws Exception{
        String inputJsonForTest1 = mapper.writeValueAsString(testNote1);
        String inputJsonForTest2 = mapper.writeValueAsString(testNote2);
        String inputJsonForTest3 = mapper.writeValueAsString(testNote3);

        mockMvc.perform(
                put("/notes")
                .content(inputJsonForTest1)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                put("/notes")
                        .content(inputJsonForTest2)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                put("/notes")
                        .content(inputJsonForTest3)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnStatusNoContentWhenDeleteIsPassedValidId() throws Exception {
        String test1 = "/notes/1";
        String test2 = "/notes/2";
        String test3 = "/notes/3";

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

        mockMvc.perform(
                delete(test3)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void setUpMockDao(){
//        dao = mock(NoteDaoJdbcTemplateImpl.class);

        Note inputNote1 = new Note();
        inputNote1.setBookId(1);
        inputNote1.setNote("This is a note.");

        Note inputNote2 = new Note();
        inputNote2.setBookId(2);
        inputNote2.setNote("This is another note.");

        Note inputNote3 = new Note();
        inputNote3.setBookId(1);
        inputNote3.setNote("Check yes or no.");

        Note outputNote1 = new Note();
        outputNote1.setNoteId(1);
        outputNote1.setBookId(1);
        outputNote1.setNote("This is a note.");

        Note outputNote2 = new Note();
        outputNote2.setNoteId(2);
        outputNote2.setBookId(2);
        outputNote2.setNote("This is another note.");

        Note outputNote3 = new Note();
        outputNote3.setNoteId(3);
        outputNote3.setBookId(1);
        outputNote3.setNote("Check yes or no.");

        List<Note> allNotes = new ArrayList<>();
        allNotes.add(outputNote1);
        allNotes.add(outputNote2);
        allNotes.add(outputNote3);

        List<Note> book1Notes = new ArrayList<>();
        book1Notes.add(outputNote1);
        book1Notes.add(outputNote3);

        List<Note> book2Notes = new ArrayList<>();
        book2Notes.add(outputNote2);

        doReturn(outputNote1).when(dao).createNote(inputNote1);
        doReturn(outputNote2).when(dao).createNote(inputNote2);
        doReturn(outputNote3).when(dao).createNote(inputNote3);
        doReturn(outputNote1).when(dao).getNote(1);
        doReturn(outputNote2).when(dao).getNote(2);
        doReturn(outputNote3).when(dao).getNote(3);
//        doThrow(JdbcOperationFailedException.class).when(service).fetchTask(4);
//        doThrow(JdbcOperationFailedException.class).when(service).updateTask(taskWithInvalidId);
//        doThrow(JdbcOperationFailedException.class).when(service).deleteTask(6);
        doReturn(allNotes).when(dao).getAllNotes();
        doReturn(book1Notes).when(dao).getNoteByBook(1);
        doReturn(book2Notes).when(dao).getNoteByBook(2);
    }

    private void setUpTestNotes(){
        testNote1 = new Note();
        testNote1.setBookId(1);
        testNote1.setNote("This is a note.");

        testNote2 = new Note();
        testNote2.setBookId(2);
        testNote2.setNote("This is another note.");

        testNote3 = new Note();
        testNote3.setBookId(1);
        testNote3.setNote("Check yes or no.");

        testNoteOutput1 = new Note();
        testNoteOutput1.setNoteId(1);
        testNoteOutput1.setBookId(1);
        testNoteOutput1.setNote("This is a note.");

        testNoteOutput2 = new Note();
        testNoteOutput2.setNoteId(2);
        testNoteOutput2.setBookId(2);
        testNoteOutput2.setNote("This is another note.");

        testNoteOutput3 = new Note();
        testNoteOutput3.setNoteId(3);
        testNoteOutput3.setBookId(1);
        testNoteOutput3.setNote("Check yes or no.");

        allTestNotes = new ArrayList<>();
        allTestNotes.add(testNoteOutput1);
        allTestNotes.add(testNoteOutput2);
        allTestNotes.add(testNoteOutput3);

        book1TestNotes = new ArrayList<>();
        book1TestNotes.add(testNoteOutput1);
        book1TestNotes.add(testNoteOutput3);

        book2TestNotes = new ArrayList<>();
        book2TestNotes.add(testNoteOutput2);
    }
}