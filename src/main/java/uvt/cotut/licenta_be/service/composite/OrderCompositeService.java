package uvt.cotut.licenta_be.service.composite;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.Order;
import uvt.cotut.licenta_be.model.OrderStatus;
import uvt.cotut.licenta_be.service.OrderService;
import uvt.cotut.licenta_be.service.api.dto.OrderCreateDTO;

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
}
