package uvt.cotut.licenta_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductCreateDTO;
import uvt.cotut.licenta_be.service.api.dto.ProductDisplayDTO;
import uvt.cotut.licenta_be.service.composite.ProductCompositeService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Tag(name = "Product")
@Slf4j
@CrossOrigin
public class ProductController {
    private final ProductCompositeService productCompositeService;

    @Operation(summary = "Insert a new product")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public Product addProduct(@RequestBody @Valid ProductCreateDTO productCreateDTO )  {
        return productCompositeService.addProduct(productCreateDTO);
    }
    
    //Just testing
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @PostMapping(consumes = "multipart/form-data")
    public void addProduct(@RequestParam("file") List<MultipartFile> file ) throws IOException {
        productCompositeService.transferPhoto(file);
    }

    @Operation(summary = "Get products with filter criteria")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @PostMapping(value = "/", produces = "application/json", consumes = "application/json", params = "limit")
    public List<ProductDisplayDTO> getFilteredProducts(@RequestBody FilterCriteriaDTO criteriaDTO,
                                                       @RequestParam("limit") @Min(value = 1, message = "Invalid data") Integer limit)  {
        return productCompositeService.getFilteredProducts(criteriaDTO, limit);
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
//    @PreAuthorize()
    @GetMapping(value = "/", produces = "application/json", params = "id")
    public Product getProductById(@RequestParam("id") @Min(value = 1, message = "Invalid data") Long id)  {
        return productCompositeService.getProductById(id);
    }
}
