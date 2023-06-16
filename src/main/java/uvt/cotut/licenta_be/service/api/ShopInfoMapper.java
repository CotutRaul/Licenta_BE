package uvt.cotut.licenta_be.service.api;

import org.mapstruct.*;
import uvt.cotut.licenta_be.model.ShopInfo;

@Mapper(componentModel = "spring")
public interface ShopInfoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShopInfo(@MappingTarget ShopInfo shopInfo, ShopInfo updatedShopInfo);
}
