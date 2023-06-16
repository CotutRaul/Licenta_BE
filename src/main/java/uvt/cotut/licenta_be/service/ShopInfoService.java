package uvt.cotut.licenta_be.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.ShopInfo;
import uvt.cotut.licenta_be.repository.ShopInfoRepository;
import uvt.cotut.licenta_be.service.api.ShopInfoMapper;

@Service
@Slf4j
@AllArgsConstructor
public class ShopInfoService {

    private final ShopInfoMapper shopInfoMapper;
    private final ShopInfoRepository shopInfoRepository;

    @Transactional
    public ShopInfo updateShopInfo(ShopInfo updatedShopInfo) {
        ShopInfo shopInfo = shopInfoRepository.findById(1L).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.SHOP_INFO_NOT_FOUND));
        shopInfoMapper.updateShopInfo(shopInfo,updatedShopInfo);

        return shopInfoRepository.save(shopInfo);
    }

    public ShopInfo getShopInfo() {
        return shopInfoRepository.findById(1L).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.SHOP_INFO_NOT_FOUND));
    }
}
