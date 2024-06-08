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
@Table(name = "merchant")
@SQLDelete(sql = "update merchant set deleted = true where id = ?")
@SQLRestriction("deleted = false")
public class Merchant extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String merchantName;
    private String merchantLocation;
    private boolean open = Boolean.FALSE;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<Product> products;
}
