package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.request.BookSearchRequest;
import com.assessment.bookstore.model.response.BookResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long searchCount(BookSearchRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM books b WHERE 1=1 ");

        if(request.getSearch() != null && !request.getSearch().isBlank()) {
            sb.append(" AND (UPPER(b.title) LIKE UPPER(:search) OR UPPER(b.author) LIKE UPPER(:search)) ");
        }
        if(request.getCategoryId() != null) {
            sb.append(" AND b.category_id = :categoryId ");
        }

        Query query = entityManager.createNativeQuery(sb.toString());

        if(request.getSearch() != null && !request.getSearch().isBlank()) {
            query.setParameter("search", "%" + request.getSearch() + "%");
        }
        if(request.getCategoryId() != null) {
            query.setParameter("categoryId", request.getCategoryId());
        }

        Object result = query.getSingleResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    @Override
    public List<BookResponse> searchData(BookSearchRequest request) {
        StringBuilder sb = getSearchQuery(request);

        Query query = entityManager.createNativeQuery(sb.toString());

        if(request.getSearch() != null && !request.getSearch().isBlank()) {
            query.setParameter("search", "%" + request.getSearch() + "%");
        }
        if(request.getCategoryId() != null) {
            query.setParameter("categoryId", request.getCategoryId());
        }

        query.setParameter("pageSize", request.getPageSize());
        query.setParameter("pageNumber", request.getPageNumber());

        List<Object[]> rows = query.getResultList();
        List<BookResponse> responseList = new ArrayList<>();

        for (Object[] row : rows) {
            BookResponse b = new BookResponse();
            b.setId(row[0].toString());
            b.setTitle((String) row[1]);
            b.setAuthor((String) row[2]);
            b.setPrice((BigDecimal) row[3]);
            b.setStock(((Integer) row[4]));
            b.setYear(row[5] != null ? ((Integer) row[5]) : null);
            b.setCategoryId(row[6].toString());
            responseList.add(b);
        }

        return responseList;
    }

    private static StringBuilder getSearchQuery(BookSearchRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.id, b.title, b.author, b.price, b.stock, b.publish_year, b.category_id ");
        sb.append("FROM books b WHERE 1=1 ");

        if(request.getSearch() != null && !request.getSearch().isBlank()) {
            sb.append(" AND (UPPER(b.title) LIKE UPPER(:search) OR UPPER(b.author) LIKE UPPER(:search)) ");
        }
        if(request.getCategoryId() != null) {
            sb.append(" AND b.category_id = :categoryId ");
        }

        sb.append(" ORDER BY b.id DESC ");
        sb.append(" LIMIT :pageSize OFFSET (:pageNumber - 1) * :pageSize ");
        return sb;
    }
}

