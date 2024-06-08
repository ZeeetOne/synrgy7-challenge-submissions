package com.example.binarfud.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
@SQLDelete(sql = "update order_details set deleted = true where id =?")
@SQLRestriction("deleted = false")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private Long totalPrice;

    private boolean deleted = Boolean.FALSE;
}
