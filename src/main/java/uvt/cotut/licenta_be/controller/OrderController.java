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
import uvt.cotut.licenta_be.model.Order;
import uvt.cotut.licenta_be.model.OrderStatus;
import uvt.cotut.licenta_be.service.api.dto.OrderCreateDTO;
import uvt.cotut.licenta_be.service.composite.OrderCompositeService;


@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Tag(name = "Order")
@Slf4j
@CrossOrigin
public class OrderController {

    private final OrderCompositeService orderCompositeService;

    @Operation(summary = "Insert a new oder")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public Order addOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO)  {
        return orderCompositeService.addOrder(orderCreateDTO);
    }

    @Operation(summary = "Insert a new oder")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update", produces = "application/json", params = {"id", "action"})
    public Order addOrder(@RequestParam("id") @Min(value = 1, message = "Invalid data") Long id, @RequestParam("action") OrderStatus action)  {
        return orderCompositeService.updateOrder(id, action);
    }
}
