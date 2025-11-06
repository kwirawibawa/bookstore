package com.assessment.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object[] getSalesReport() {
        String jpql = "SELECT SUM(o.totalPrice), SUM(oi.quantity) " +
                "FROM Order o JOIN o.items oi " +
                "WHERE o.status = 'PAID'";
        Query query = em.createQuery(jpql);
        return (Object[]) query.getSingleResult();
    }

    @Override
    public List<Object[]> getBestSellerBooks(int limit) {
        String jpql = "SELECT oi.book.title, SUM(oi.quantity) as totalSold " +
                "FROM OrderItem oi JOIN oi.order o " +
                "WHERE o.status = 'PAID' " +
                "GROUP BY oi.book.id, oi.book.title " +
                "ORDER BY totalSold DESC";
        Query query = em.createQuery(jpql);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Object[] getPriceReport() {
        String jpql = "SELECT MAX(b.price), MIN(b.price), AVG(b.price) FROM Book b";
        Query query = em.createQuery(jpql);
        return (Object[]) query.getSingleResult();
    }
}
