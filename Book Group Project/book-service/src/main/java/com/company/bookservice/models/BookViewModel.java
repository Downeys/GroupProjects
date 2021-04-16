package com.company.bookservice.models;

import com.company.bookservice.feign.NoteServiceFeignClient;

import java.util.List;
import java.util.Objects;

public class BookViewModel {
    private Integer bookId;
    private String title;
    private String author;
    private List<String> notes;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookViewModel that = (BookViewModel) o;
        return Objects.equals(bookId, that.bookId) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, notes);
    }

    @Override
    public String toString() {
        return "BookViewModel{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                '}';
    }
}
