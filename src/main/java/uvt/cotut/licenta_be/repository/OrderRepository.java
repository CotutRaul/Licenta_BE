package uvt.cotut.licenta_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uvt.cotut.licenta_be.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
