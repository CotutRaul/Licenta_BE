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
import uvt.cotut.licenta_be.model.Category;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.model.SubCategory;
import uvt.cotut.licenta_be.repository.CategoryRepository;
import uvt.cotut.licenta_be.repository.ProductRepository;
import uvt.cotut.licenta_be.repository.SubCategoryRepository;
import uvt.cotut.licenta_be.service.api.ProductMapper;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinaryService;

    @Transactional
    public Product addProduct(ProductCreateDTO productCreateDTO) {
        Product product = productMapper.toEntity(productCreateDTO);
        product.setCreatedDate(LocalDateTime.now());

        handleCategorySubCategory(product, productCreateDTO.getSubCategory(), productCreateDTO.getCategory());

        return productRepository.save(product);
    }

    @Transactional
    public Product editProduct(Long id, ProductCreateDTO productCreateDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        productMapper.editProductFromCreateDto(product,productCreateDTO);

        handleCategorySubCategory(product, productCreateDTO.getSubCategory(), productCreateDTO.getCategory());

        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void handleCategorySubCategory(Product product, String subCategoryName, String categoryName) {
        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName);
        if (subCategory != null) {
            product.setSubCategory(subCategory);
        }
        else {
            Category category = categoryRepository.findByName(categoryName);
            if (category != null) {
                SubCategory newSubCategory = new SubCategory();
                newSubCategory.setCategory(category);
                newSubCategory.setName(subCategoryName);
                product.setSubCategory(subCategoryRepository.save(newSubCategory));
            }
            else {
                Category newCategory = new Category();
                newCategory.setName(categoryName);
                SubCategory newSubCategory = new SubCategory();
                newSubCategory.setCategory(categoryRepository.save(newCategory));
                newSubCategory.setName(subCategoryName);
                product.setSubCategory(newSubCategory);
            }
        }
    }


    public List<ProductDisplayDTO> getFilteredProducts(FilterCriteriaDTO criteriaDTO, Integer limit) {
        return productRepository.findProductsFiltered(criteriaDTO, limit).stream().map(productMapper::toDisplayDTO).toList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinaryService.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        return (String) uploadResult.get("secure_url");
    }

    public Map<String, List<String>> getAllCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        Map<String, List<String>> categoriesMap = new HashMap<>();

        for (SubCategory subCategory : subCategories) {
            if (!categoriesMap.containsKey(subCategory.getCategory().getName())) {
                categoriesMap.put(subCategory.getCategory().getName(), new ArrayList<>());
            }
            categoriesMap.get(subCategory.getCategory().getName()).add(subCategory.getName());
        }

        return categoriesMap;
    }

    public List<ProductDisplayDTO> getProductsById(List<Long> listOfIds) {
        return productRepository.findAllById(listOfIds).stream().map(productMapper::toDisplayDTO).toList();
    }
}
