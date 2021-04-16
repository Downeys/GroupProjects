package com.company.noteservice.dao;

import com.company.noteservice.models.Note;

import java.util.List;

public interface NoteDao {

    Note createNote(Note note);
    Note getNote(int noteId);
    List<Note> getNoteByBook(int bookId);
    List<Note> getAllNotes();
    void updateNote(Note note);
    void deleteNote(int noteId);

}
