package com.binarfud.binarfud_service.entity;

import com.binarfud.binarfud_service.entity.accounts.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@SQLDelete(sql = "update orders set deleted = true where id = ?")
@SQLRestriction("deleted = false")
public class Order extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "destination_address")
    private String destinationAddress;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_completed")
    private boolean isCompleted = Boolean.FALSE;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
