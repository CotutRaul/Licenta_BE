package uvt.cotut.licenta_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uvt.cotut.licenta_be.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
