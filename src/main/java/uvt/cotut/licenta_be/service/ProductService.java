package uvt.cotut.licenta_be.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.repository.ProductRepository;
import uvt.cotut.licenta_be.service.api.ProductMapper;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final Cloudinary cloudinaryService;

    @Transactional
    public Product addProduct(ProductCreateDTO productCreateDTO) {
        Product product = productMapper.toEntity(productCreateDTO);
        product.setAvailable(true);
        product.setCreatedDate(LocalDateTime.now());

        return productRepository.save(product);
    }


    public List<ProductDisplayDTO> getFilteredProducts(FilterCriteriaDTO criteriaDTO, Integer limit) {
        return productRepository.findProductsFiltered(criteriaDTO, limit).stream().map(productMapper::toDisplayDTO).toList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinaryService.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        return (String) uploadResult.get("secure_url");
    }
}
