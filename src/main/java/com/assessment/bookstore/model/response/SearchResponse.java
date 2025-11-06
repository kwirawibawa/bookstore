package com.assessment.bookstore.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {

    @NonNull
    private Integer pageNo;

    @NonNull
    private Integer pageSize;

    @NonNull
    private Integer totalDataInPage;

    @NonNull
    private Long totalData;

    @NonNull
    private Long totalPages;

    @NonNull
    private Integer startData;

    @NonNull
    private Integer endData;

    @NonNull
    private List<T> listData;

    private int code;

    private String message;
}

