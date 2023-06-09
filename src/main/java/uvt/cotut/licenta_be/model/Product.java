package uvt.cotut.licenta_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Float price;

    @Column
    private Float originalPrice;

//    @Column(nullable = false)
//    private Long amount;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean available;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SubCategoryId")
    private SubCategory subCategory;

    private LocalDateTime createdDate;

    private String imageCardPath;
}
