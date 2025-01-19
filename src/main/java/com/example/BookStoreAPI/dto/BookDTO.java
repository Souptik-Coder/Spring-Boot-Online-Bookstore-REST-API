package com.example.BookStoreAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BookDTO {
    private Long id;
//    @JsonProperty("book_title")
    private String title;
    private String author;
    private Double price;
    private String isbn;
}
