package com.company.noteservice.dao;

import com.company.noteservice.models.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoJdbcTemplateImplTest {

    @Autowired
    NoteDao dao;

    Note inputNote1, inputNote2, inputNote3, outputNote1, outputNote2, outputNote3;

    @Before
    public void setUp() throws Exception {
        List<Note> notes = dao.getAllNotes();
        for (Note n :notes) {
            dao.deleteNote(n.getNoteId());
        }
        setUpNotes();
    }

    @Test
    public void createNote() {
        //Act
        inputNote1 = dao.createNote(inputNote1);

        Note noteThatWasGotten = dao.getNote(inputNote1.getNoteId());

        //Assert
        assertEquals(inputNote1, noteThatWasGotten);

        //Act
        dao.deleteNote(inputNote1.getNoteId());

        noteThatWasGotten = dao.getNote(inputNote1.getNoteId());

        //Assert
        assertNull(noteThatWasGotten);
    }

    @Test
    public void getNoteByBook() {
        inputNote1 = dao.createNote(inputNote1);
        inputNote2 = dao.createNote(inputNote2);
        inputNote3 = dao.createNote(inputNote3);

        //Act
        List<Note> resultFromTest1 = dao.getNoteByBook(1);
        List<Note> resultFromTest2 = dao.getNoteByBook(2);

        //Assert
        assertEquals(2, resultFromTest1.size());
        assertEquals(1, resultFromTest1.get(0).getBookId().longValue());
        assertEquals(1, resultFromTest1.get(1).getBookId().longValue());

        assertEquals(1, resultFromTest2.size());
        assertEquals(2, resultFromTest2.get(0).getBookId().longValue());
    }

    @Test
    public void getAllNotes() {

        inputNote1 = dao.createNote(inputNote1);

        //Act
        List<Note> allNotesInDatabase = dao.getAllNotes();

        //Assert
        assertEquals(1, allNotesInDatabase.size());

        inputNote2 = dao.createNote(inputNote2);

        //Act
        allNotesInDatabase = dao.getAllNotes();

        //Assert
        assertEquals(2, allNotesInDatabase.size());

        inputNote3 = dao.createNote(inputNote3);

        //Act
        allNotesInDatabase = dao.getAllNotes();

        //Assert
        assertEquals(3, allNotesInDatabase.size());

        dao.deleteNote(inputNote1.getNoteId());
        dao.deleteNote(inputNote3.getNoteId());

        //Act
        allNotesInDatabase = dao.getAllNotes();

        //Assert
        assertEquals(1, allNotesInDatabase.size());
        assertEquals(inputNote2, allNotesInDatabase.get(0));
    }

    @Test
    public void updateNote() {
        Note expectedOutputFromTest = dao.createNote(inputNote1);
        expectedOutputFromTest.setNote("This is a test note.");

        //Act
        dao.updateNote(expectedOutputFromTest);

        Note actualOutputFromTest = dao.getNote(inputNote1.getNoteId());

        //Assert
        assertEquals(expectedOutputFromTest, actualOutputFromTest);
    }

    @Test
    public void deleteNote() {
        inputNote1 = dao.createNote(inputNote1);
        inputNote2 = dao.createNote(inputNote2);
        inputNote3 = dao.createNote(inputNote3);
        List<Note> noteList = dao.getAllNotes();
        assertEquals(3, noteList.size());


        //Act
        dao.deleteNote(inputNote2.getNoteId());
        noteList = dao.getAllNotes();

        //Assert
        assertEquals(2, noteList.size());
        assertNotEquals(inputNote2, noteList.get(0));
        assertNotEquals(inputNote2, noteList.get(1));

        //Act
        dao.deleteNote(inputNote1.getNoteId());
        noteList = dao.getAllNotes();

        //Assert
        assertEquals(1, noteList.size());
        assertNotEquals(inputNote1, noteList.get(0));

        //Act
        dao.deleteNote(inputNote3.getNoteId());
        noteList = dao.getAllNotes();

        //Assert
        assertEquals(0, noteList.size());
    }

    private void setUpNotes(){
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
    }
}