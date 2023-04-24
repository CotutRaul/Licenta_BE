package uvt.cotut.licenta_be.repository;

import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> findProductsFiltered(FilterCriteriaDTO dto, Integer limit);

}
