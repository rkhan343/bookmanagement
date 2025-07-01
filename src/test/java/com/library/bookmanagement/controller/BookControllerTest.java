package com.library.bookmanagement.controller;

import com.library.bookmanagement.dto.BookResponse;
import com.library.bookmanagement.exception.ResourceNotFoundException;
import com.library.bookmanagement.model.Book;
import com.library.bookmanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book sampleBook;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("Test Author");
        sampleBook.setIsbn("ISBN123");
        sampleBook.setPublishedDate(LocalDate.of(2023, 1, 1));
    }

    @Test
    public void testGetAllBooks_Success() {
        List<Book> books = Arrays.asList(sampleBook);
        when(bookService.findAll()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookService, times(1)).findAll();
    }

    @Test
    public void testGetAllBooks_ThrowsResourceNotFoundException() {
        when(bookService.findAll()).thenThrow(new ResourceNotFoundException("No books found"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookController.getAllBooks();
        });

        assertEquals("No books found", exception.getMessage());
        verify(bookService, times(1)).findAll();
    }

    @Test
    public void testGetBookById_Success() {
        when(bookService.findById(1L)).thenReturn(sampleBook);

        Book result = bookController.getBookById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void testGetBookById_ThrowsResourceNotFoundException() {
        when(bookService.findById(1L)).thenThrow(new ResourceNotFoundException("Book not found with id 1"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookController.getBookById(1L);
        });

        assertEquals("Book not found with id 1", exception.getMessage());
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void testCreateBook_Success() {
        when(bookService.save(sampleBook)).thenReturn(sampleBook);

        Book result = bookController.createBook(sampleBook);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookService, times(1)).save(sampleBook);
    }

    @Test
    public void testUpdateBook_Success() {
        when(bookService.update(1L, sampleBook)).thenReturn(sampleBook);

        Book result = bookController.updateBook(1L, sampleBook);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookService, times(1)).update(1L, sampleBook);
    }

    @Test
    public void testDeleteBook_Success() {
        // delete returns void, so just verify interaction
        doNothing().when(bookService).delete(1L);

        // Call the controller method
        var responseEntity = bookController.deleteBook(1L);

        // Verify service called once
        verify(bookService, times(1)).delete(1L);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Book deleted successfully", responseEntity.getBody().getMessage());
    }
}