package ua.com.diplomka.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.diplomka.orderservice.enums.OrderStatus;
import ua.com.diplomka.orderservice.enums.WorkType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false)
    private WorkType workType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;

    private String comment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderFile> files = new ArrayList<>();
}
