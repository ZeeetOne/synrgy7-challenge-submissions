package com.example.binarfud.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@SQLDelete(sql = "update products set deleted = true where id =?")
@SQLRestriction("deleted = false")
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productName;
    private Long price;

    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    public enum ProductCategory{
        FOODS,
        BEVERAGES
    }

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
