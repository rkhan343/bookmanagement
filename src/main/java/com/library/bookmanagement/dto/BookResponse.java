package com.library.bookmanagement.dto;

import com.library.bookmanagement.model.Book;

import java.util.List;

public class BookResponse {
    private List<Book> books;
    private String message;
    private int status;

    public BookResponse(List<Book> books, String message, int status) {
        this.books = books;
        this.message = message;
        this.status = status;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}

