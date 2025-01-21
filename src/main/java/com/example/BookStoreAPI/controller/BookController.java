package com.example.BookStoreAPI.controller;

import com.example.BookStoreAPI.dto.BookDTO;
import com.example.BookStoreAPI.model.Book;
import com.example.BookStoreAPI.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;  // Injecting service instead of repository

    @GetMapping
    @Operation(summary = "Get all Books")
    public ResponseEntity<List<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> books = bookService.getAllBooks().stream()
                .map(
                        book -> EntityModel.of(book,
                                linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books")))
                .toList();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(books.size()));
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Book by Id")
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@PathVariable Long id) {
        Optional<BookDTO> book = bookService.getBookById(id);
        return book.map(b -> {
            EntityModel<BookDTO> resource = EntityModel.of(b,
                    linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
                    linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Book")
    public ResponseEntity<EntityModel<BookDTO>> createBook(@RequestBody BookDTO book) {
        BookDTO createdBook = bookService.createBook(book);
        EntityModel<BookDTO> resource = EntityModel.of(createdBook,
                linkTo(methodOn(BookController.class).getBookById(createdBook.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/books/" + createdBook.getId());
        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Book")
    public ResponseEntity<EntityModel<BookDTO>> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBook) {
        return bookService.updateBook(id, updatedBook)
                .map(book -> {
                    EntityModel<BookDTO> resource = EntityModel.of(book,
                            linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                            linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
                    return ResponseEntity.ok(resource);
                })
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
    public List<EntityModel<BookDTO>> getBooks(@RequestParam(required = false) String title,
                                               @RequestParam(required = false) String author) {
        return bookService.getFilteredBooks(title, author).stream().map(
                book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                        linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"))
        ).toList();
    }
}
