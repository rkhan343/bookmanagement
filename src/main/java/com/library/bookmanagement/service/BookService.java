package com.library.bookmanagement.service;


import com.library.bookmanagement.exception.ResourceNotFoundException;
import com.library.bookmanagement.model.Book;
import com.library.bookmanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.library.bookmanagement.exception.CustomHttpExceptions.*;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> findAll() {
        List<Book> books = repo.findAll();
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found");
        }
        return books;
    }

    public Book findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public Book save(Book book) {
        // ✅ Example check: throw ConflictException if ISBN already exists
        if (repo.existsByIsbn(book.getIsbn())) {
            throw new ConflictException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        // ✅ Validate fields manually if needed
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Title must not be empty");
        }

        return repo.save(book);
    }

    public Book update(Long id, Book updatedBook) {
        Book book = findById(id);

        if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty()) {
            throw new BadRequestException("Title must not be empty");
        }

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setPublishedDate(updatedBook.getPublishedDate());

        return repo.save(book);
    }

    public void delete(Long id) {
        Book book = findById(id);
        repo.delete(book);
    }
}

