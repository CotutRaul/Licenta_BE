package uvt.cotut.licenta_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.ProductDTO;
import uvt.cotut.licenta_be.service.composite.ProductCompositeService;


@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Tag(name = "Product")
@Slf4j
public class ProductController {
    private final ProductCompositeService productCompositeService;

    @Operation(summary = "Insert a new product")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public Product addProduct(@RequestBody @Valid ProductDTO productDTO )  {
        return productCompositeService.addProduct(productDTO);
    }
}
