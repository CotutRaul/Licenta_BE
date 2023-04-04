package uvt.cotut.licenta_be.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.repository.ProductRepository;
import uvt.cotut.licenta_be.service.api.ProductMapper;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Product addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setAvailable(true);
        product.setCreatedDate(LocalDateTime.now());

        return productRepository.save(product);
    }

    public List<Product> getFilteredProducts(FilterCriteriaDTO criteriaDTO) {
        return productRepository.findProductsFiltered(criteriaDTO);
    }
}
