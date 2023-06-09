package uvt.cotut.licenta_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderAmount> orderAmountList;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressId")
    private Address address;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column
    private LocalDateTime deliveredDate;

    @Column(nullable = false)
    private OrderStatus status;

}
