package uvt.cotut.licenta_be.service.composite;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.ProductService;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDTO;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductCompositeService {

    private final ProductService productService;

    public Product addProduct(ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    public List<Product> getFilteredProducts(FilterCriteriaDTO criteriaDTO) {
        return productService.getFilteredProducts(criteriaDTO);
    }
}
