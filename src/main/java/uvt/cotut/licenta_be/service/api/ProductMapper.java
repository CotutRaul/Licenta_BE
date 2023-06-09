package uvt.cotut.licenta_be.service.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "subCategory", ignore = true)
    @Mapping(target = "available", defaultValue = "true")
    Product toEntity(ProductCreateDTO productCreateDTO);

    ProductDisplayDTO toDisplayDTO(Product product);
}
