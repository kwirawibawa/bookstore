package com.assessment.bookstore.model.response;

import com.assessment.bookstore.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private String id;
    private String name;
    private List<Book> books;
}
