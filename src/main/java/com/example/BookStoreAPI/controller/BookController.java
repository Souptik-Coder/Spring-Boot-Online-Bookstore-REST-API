package com.example.BookStoreAPI.controller;

import com.example.BookStoreAPI.model.Book;
import com.example.BookStoreAPI.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;  // Injecting service instead of repository

    @GetMapping
    @Operation(summary = "Get all Books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(books.size()));
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Book by Id")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/books/" + createdBook.getId());
        return new ResponseEntity<>(createdBook, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Book")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Book")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok("Book with ID " + id + " deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Get filtered Books")
    public List<Book> getBooks(@RequestParam(required = false) String title,
                               @RequestParam(required = false) String author) {
        return bookService.getFilteredBooks(title, author);
    }
}
