package uvt.cotut.licenta_be.service.composite;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.ShopInfo;
import uvt.cotut.licenta_be.service.ShopInfoService;

@Service
@Slf4j
@AllArgsConstructor
public class ShopInfoCompositeService {

    private final ShopInfoService shopInfoService;


    public ShopInfo updateShopInfo(ShopInfo updatedShopInfo) {
        return shopInfoService.updateShopInfo(updatedShopInfo);
    }

    public ShopInfo getShopInfo() {
        return shopInfoService.getShopInfo();
    }
}
