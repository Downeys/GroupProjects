package com.company.bookservice.feign;

import com.company.bookservice.models.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "note-service")
public interface NoteServiceFeignClient {

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note createNote(@RequestBody Note note);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    public Note getNoteByNoteId(@PathVariable int id);

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<Note> getNotesByBookId(@PathVariable int book_id);

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public List<Note> getAllNotes();

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public void updateNote(@RequestBody Note note, @PathVariable int id);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable int id);

}
