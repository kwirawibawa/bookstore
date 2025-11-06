package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findOrders(UUID userId, boolean isAdmin, int pageNumber, int pageSize) {
        StringBuilder sb = new StringBuilder("SELECT o FROM Order o ");

        if (!isAdmin) {
            sb.append("WHERE o.user.id = :userId ");
        }

        sb.append("ORDER BY o.createdAt DESC");

        Query query = em.createQuery(sb.toString(), Order.class);

        if (!isAdmin) {
            query.setParameter("userId", userId);
        }

        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public long countOrders(UUID userId, boolean isAdmin) {
        StringBuilder sb = new StringBuilder("SELECT COUNT(o) FROM Order o ");

        if (!isAdmin) {
            sb.append("WHERE o.user.id = :userId ");
        }

        Query query = em.createQuery(sb.toString());

        if (!isAdmin) {
            query.setParameter("userId", userId);
        }

        return (Long) query.getSingleResult();
    }
}
