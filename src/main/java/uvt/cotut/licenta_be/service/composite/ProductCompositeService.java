package uvt.cotut.licenta_be.service.composite;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.ProductService;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ProductCompositeService {

    private final ProductService productService;

    public Product addProduct(ProductCreateDTO productCreateDTO) {
        return productService.addProduct(productCreateDTO);
    }

    public List<ProductDisplayDTO> getFilteredProducts(FilterCriteriaDTO criteriaDTO, Integer limit) {
        return productService.getFilteredProducts(criteriaDTO, limit);
    }

    public Product getProductById(Long id) {
        return productService.getProductById(id);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return productService.uploadImage(file);
    }

    public Map<String, List<String>> getAllCategories() {
        return productService.getAllCategories();
    }

    public List<ProductDisplayDTO> getProductsById(List<Long> listOfIds) {
        return productService.getProductsById(listOfIds);
    }
}
