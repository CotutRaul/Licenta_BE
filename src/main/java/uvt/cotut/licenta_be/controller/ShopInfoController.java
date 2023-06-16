package uvt.cotut.licenta_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uvt.cotut.licenta_be.model.ShopInfo;
import uvt.cotut.licenta_be.service.composite.ShopInfoCompositeService;

@RestController
@RequestMapping("/shopInfo")
@AllArgsConstructor
@Tag(name = "ShopInfo")
@Slf4j
@CrossOrigin
public class ShopInfoController {
    private final ShopInfoCompositeService shopInfoCompositeService;

    @Operation(summary = "Edit ShopInfo")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ShopInfo updateShopInfo(@RequestBody ShopInfo updateShopInfo)  {
        return shopInfoCompositeService.updateShopInfo(updateShopInfo);
    }

    @Operation(summary = "Get shopInfo")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @GetMapping(value = "", produces = "application/json")
    public ShopInfo getShopInfo()  {
        return shopInfoCompositeService.getShopInfo();
    }

}
