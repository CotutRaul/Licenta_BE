package uvt.cotut.licenta_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean available;

    @ManyToMany
    private List<Category> categoryList;

    private LocalDateTime createdDate;

    private String imageCardPath;
}
