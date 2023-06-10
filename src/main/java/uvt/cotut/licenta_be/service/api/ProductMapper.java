package uvt.cotut.licenta_be.service.api;

import org.mapstruct.*;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "subCategory", ignore = true)
    @Mapping(target = "available", defaultValue = "true")
    Product toEntity(ProductCreateDTO productCreateDTO);

    ProductDisplayDTO toDisplayDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "subCategory", ignore = true)
    void editProductFromCreateDto(@MappingTarget Product product, ProductCreateDTO productCreateDTO);
}
