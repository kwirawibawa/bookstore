package com.assessment.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends AuditTrail {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    private Integer publishYear;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Lob
    private String imageBase64;

    @OneToMany(mappedBy = "book")
    private List<OrderItem> orderItems;
}