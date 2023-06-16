package uvt.cotut.licenta_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uvt.cotut.licenta_be.model.ShopInfo;

public interface ShopInfoRepository extends JpaRepository<ShopInfo, Long> {
}
