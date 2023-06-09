package uvt.cotut.licenta_be.service.api;

import org.mapstruct.Mapper;
import uvt.cotut.licenta_be.model.Order;
@Mapper(componentModel = "spring")
public interface OrderMapper {
    default void handleOrderRelations(Order order) {
        if (!order.getOrderAmountList().isEmpty()) {
            order.getOrderAmountList().forEach(a -> a.setOrder(order));
        }
    }
}
