package com.company.noteservice.dao;

import com.company.noteservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcTemplateImpl implements NoteDao{

    public static final String INSERT_NOTE =
            "insert into note (book_id, note) values (?, ?)";
    public static final String SELECT_NOTE_BY_ID =
            "select * from note where note_id = ?";
    public static final String SELECT_ALL_NOTES =
            "select * from note";
    public static final String SELECT_NOTES_BY_BOOK_ID =
            "select * from note where book_id = ?";
    public static final String UPDATE_NOTE =
            "update note set book_id = ?, note = ? where note_id = ?";
    public static final String DELETE_NOTE =
            "delete from note where note_id = ?";

    JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Note createNote(Note note) {
        jdbcTemplate.update(INSERT_NOTE, note.getBookId(), note.getNote());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        note.setNoteId(id);
        return note;
    }

    @Override
    public Note getNote(int noteId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_NOTE_BY_ID, this::mapRowToNote, noteId);
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Note> getNoteByBook(int bookId) {
        return jdbcTemplate.query(SELECT_NOTES_BY_BOOK_ID, this::mapRowToNote, bookId);
    }

    @Override
    public List<Note> getAllNotes() {
        return jdbcTemplate.query(SELECT_ALL_NOTES, this::mapRowToNote);
    }

    @Override
    public void updateNote(Note note) {
        jdbcTemplate.update(UPDATE_NOTE, note.getBookId(), note.getNote(), note.getNoteId());
    }

    @Override
    public void deleteNote(int noteId) {
        jdbcTemplate.update(DELETE_NOTE, noteId);
    }

    private Note mapRowToNote(ResultSet rs, int rowNum) throws SQLException{
        Note n = new Note();
        n.setNoteId(rs.getInt("note_id"));
        n.setBookId(rs.getInt("book_id"));
        n.setNote(rs.getString("note"));
        return n;
    }
}
