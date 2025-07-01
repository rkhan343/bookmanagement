package com.library.bookmanagement.controller;

import com.library.bookmanagement.dto.BookResponse;
import com.library.bookmanagement.model.Book;
import com.library.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/fetch-all-book")
    public List<Book> getAllBooks() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return service.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return service.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BookResponse> deleteBook(@PathVariable Long id) {
        service.delete(id);
       // BookResponse response = new BookResponse(id, "Book deleted successfully");
        BookResponse response = new BookResponse(new ArrayList<>(), "Book deleted successfully", 200);
        return ResponseEntity.ok(response);
    }
}

