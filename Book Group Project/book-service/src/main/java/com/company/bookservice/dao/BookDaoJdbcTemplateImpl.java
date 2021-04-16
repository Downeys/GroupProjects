package com.company.bookservice.dao;

import com.company.bookservice.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJdbcTemplateImpl implements BookDao{

    public static final String INSERT_BOOK =
            "insert into book (title, author) values (?, ?)";
    public static final String SELECT_BOOK_BY_ID =
            "select * from book where book_id = ?";
    public static final String SELECT_ALL_BOOKS =
            "select * from book";
    public static final String UPDATE_BOOK =
            "update book set title = ?, author = ? where book_id = ?";
    public static final String DELETE_BOOK =
            "delete from book where book_id = ?";

    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book createBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK, book.getTitle(), book.getAuthor());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);
        book.setBookId(id);
        return book;    }

    @Override
    public Book getBook(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID, this::mapRowToBook, id);
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS, this::mapRowToBook);
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(UPDATE_BOOK, book.getTitle(), book.getAuthor(), book.getBookId());

    }

    @Override
    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException{
        Book b = new Book();
        b.setBookId(rs.getInt("book_id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        return b;
    }
}
