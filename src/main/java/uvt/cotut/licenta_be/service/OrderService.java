package uvt.cotut.licenta_be.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.*;
import uvt.cotut.licenta_be.notification.service.EmailSenderService;
import uvt.cotut.licenta_be.repository.OrderRepository;
import uvt.cotut.licenta_be.repository.ProductRepository;
import uvt.cotut.licenta_be.security.SecurityHelper;
import uvt.cotut.licenta_be.service.api.OrderMapper;
import uvt.cotut.licenta_be.service.api.dto.CartDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    private final EmailSenderService emailSenderService;

    public Order addOrder(List<CartDTO> cartDTOS, Address address) {
        User userDetails = SecurityHelper.getUserDetails();
        if (userDetails == null) {
            throw new ApplicationBusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Order newOrder = new Order();
        newOrder.setUser(userDetails);
        newOrder.setAddress(address);
        newOrder.setOrderDate(LocalDateTime.now());

        List<OrderAmount> orderAmounts = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOS) {
            Product product = productRepository.findById(cartDTO.getProductId())
                    .orElseThrow(() -> new ApplicationBusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderAmount orderAmount = new OrderAmount();
            orderAmount.setProduct(product);
            orderAmount.setPrice(product.getPrice());
            orderAmount.setOriginalPrice(product.getOriginalPrice());
            orderAmount.setAmount(cartDTO.getAmount());

            orderAmounts.add(orderAmount);
        }

        newOrder.setOrderAmountList(orderAmounts);
        newOrder.setStatus(OrderStatus.ORDERED);
        orderMapper.handleOrderRelations(newOrder);

        Order save = orderRepository.save(newOrder);

        emailSenderService.sendConfirmationEmail(userDetails.getEmail(), save);
        return save;
    }

    public Order updateOrder(Long id, OrderStatus action) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.ORDER_NOT_FOUND));
        User userDetails = SecurityHelper.getUserDetails();

        if ( !((userDetails.getId() == order.getUser().getId() && action == OrderStatus.CANCELED) || userDetails.getRole() == Role.ADMIN)) {
            throw new ApplicationBusinessException(ErrorCode.ACCESS_DENIED);
        }


        if (action == OrderStatus.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
        }
        order.setStatus(action);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.ORDER_NOT_FOUND));
        User userDetails = SecurityHelper.getUserDetails();

        if ( !(userDetails.getId() == order.getUser().getId() || userDetails.getRole() == Role.ADMIN)) {
            throw new ApplicationBusinessException(ErrorCode.ACCESS_DENIED);
        }

        return order;
    }
}
