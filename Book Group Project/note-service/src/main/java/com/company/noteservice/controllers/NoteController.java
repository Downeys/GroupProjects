package com.company.noteservice.controllers;

import com.company.noteservice.dao.NoteDao;
import com.company.noteservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RefreshScope
public class NoteController {

    private NoteDao dao;

    @Autowired
    public  NoteController(NoteDao dao){
        this.dao = dao;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note createNote(@RequestBody Note note){
        return null;
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Note getNoteByNoteId(@PathVariable int id){
        return null;
    }

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> getNotesByBookId(@PathVariable int book_id){
        return null;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> getAllNotes(){
        return null;
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateNote(@RequestBody Note note, @PathVariable int id){

    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable int id){

    }

}
