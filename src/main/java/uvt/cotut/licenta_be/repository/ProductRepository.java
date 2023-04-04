package uvt.cotut.licenta_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uvt.cotut.licenta_be.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
}
