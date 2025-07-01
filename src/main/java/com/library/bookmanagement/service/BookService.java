package com.library.bookmanagement.service;

import static com.library.bookmanagement.exception.CustomHttpExceptions.*;
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
        validateBook(book, false);

        if (repo.existsByIsbn(book.getIsbn())) {
            throw new ConflictException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        return repo.save(book);
    }

    public Book update(Long id, Book updatedBook) {
        Book existing = findById(id);

        validateBook(updatedBook, true);

        // Prevent ISBN duplication (excluding current record)
        if (!existing.getIsbn().equals(updatedBook.getIsbn())
                && repo.existsByIsbn(updatedBook.getIsbn())) {
            throw new ConflictException("Another book with ISBN " + updatedBook.getIsbn() + " already exists");
        }

        existing.setTitle(updatedBook.getTitle().trim());
        existing.setAuthor(updatedBook.getAuthor().trim());
        existing.setIsbn(updatedBook.getIsbn().trim());
        existing.setPublishedDate(updatedBook.getPublishedDate());

        return repo.save(existing);
    }

    public void delete(Long id) {
        Book book = findById(id);
        repo.delete(book);
    }

    // ✅ Reusable validation helper
    private void validateBook(Book book, boolean isUpdate) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Title must not be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new BadRequestException("Author must not be empty");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new BadRequestException("ISBN must not be empty");
        }
    }
}
