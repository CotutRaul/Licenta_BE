package uvt.cotut.licenta_be.service.composite;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.Order;
import uvt.cotut.licenta_be.model.OrderStatus;
import uvt.cotut.licenta_be.service.OrderService;
import uvt.cotut.licenta_be.service.api.dto.DateRangeDTO;
import uvt.cotut.licenta_be.service.api.dto.OrderCreateDTO;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderCompositeService {

    private final OrderService orderService;

    public Order addOrder(OrderCreateDTO orderCreateDTO) {
        return orderService.addOrder(orderCreateDTO.getCartDTOS(), orderCreateDTO.getAddress());
    }

    public Order updateOrder(Long id, OrderStatus action) {
        return orderService.updateOrder(id, action);
    }

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    public List<Order> getAllOrdersByClient() {
        return orderService.getAllOrdersByClient();
    }

    public ResponseEntity<byte[]> downloadExcelFile(DateRangeDTO dateRangeDTO) {
        return orderService.downloadExcelFile(dateRangeDTO);
    }
}
