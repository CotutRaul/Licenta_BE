package uvt.cotut.licenta_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDTO;
import uvt.cotut.licenta_be.service.composite.ProductCompositeService;

import java.io.IOException;
import java.util.List;


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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public Product addProduct(@RequestBody @Valid ProductDTO productDTO )  {
        return productCompositeService.addProduct(productDTO);
    }
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @PostMapping(consumes = "multipart/form-data")
    public void addProduct(@RequestParam("file") List<MultipartFile> file ) throws IOException {
        productCompositeService.transferPhoto(file);
    }

    @Operation(summary = "Get items with filter criteria")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @GetMapping(value = "/", produces = "application/json", consumes = "application/json")
    public List<Product> getFilteredProducts(@RequestBody FilterCriteriaDTO criteriaDTO)  {
        return productCompositeService.getFilteredProducts(criteriaDTO);
    }
}
