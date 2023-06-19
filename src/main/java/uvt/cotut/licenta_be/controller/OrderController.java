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

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Tag(name = "Order")
@Slf4j
@CrossOrigin
public class OrderController {

    private final OrderCompositeService orderCompositeService;

    @Operation(summary = "Insert a new order")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public Order addOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO)  {
        return orderCompositeService.addOrder(orderCreateDTO);
    }

    @Operation(summary = "Update an order")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update", produces = "application/json", params = {"id", "action"})
    public Order updateOrder(@RequestParam("id") @Min(value = 1, message = "Invalid data") Long id, @RequestParam("action") OrderStatus action)  {
        return orderCompositeService.updateOrder(id, action);
    }

    @Operation(summary = "Get order by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/", produces = "application/json", params = "id")
    public Order getOrderById(@RequestParam("id") @Min(value = 1, message = "Invalid data") Long id)  {
        return orderCompositeService.getOrderById(id);
    }

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/all", produces = "application/json")
    public List<Order> getAllOrders()  {
        return orderCompositeService.getAllOrders();
    }

    @Operation(summary = "Get all orders made by a client")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/allByClient", produces = "application/json")
    public List<Order> getAllOrdersByClient()  {
        return orderCompositeService.getAllOrdersByClient();
    }
}
