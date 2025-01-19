package com.example.BookStoreAPI.service;

import com.example.BookStoreAPI.dto.BookDTO;
import com.example.BookStoreAPI.model.Book;
import com.example.BookStoreAPI.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class));
    }

    public BookDTO createBook(BookDTO book) {
        Book savedBook = bookRepository.save(modelMapper.map(book, Book.class));
        return modelMapper.map(savedBook, BookDTO.class);
    }

    public Optional<BookDTO> updateBook(Long id, BookDTO updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    book.setIsbn(updatedBook.getIsbn());
                    bookRepository.save(book);
                    return modelMapper.map(book, BookDTO.class);
                });
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BookDTO> getFilteredBooks(String title, String author) {
        List<Book> result;
        if (title != null && author != null) {
            result = bookRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            result = bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            result = bookRepository.findByAuthorContaining(author);
        } else {
            result = bookRepository.findAll();
        }

        return result.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }
}
