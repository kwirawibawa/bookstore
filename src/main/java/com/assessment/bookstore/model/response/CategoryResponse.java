package com.assessment.bookstore.model.response;

import com.assessment.bookstore.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private String id;
    private String name;
    private List<Book> books;
}
