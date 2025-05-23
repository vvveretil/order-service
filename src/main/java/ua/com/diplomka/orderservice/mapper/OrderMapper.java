package ua.com.diplomka.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import ua.com.diplomka.orderservice.dto.request.CreateOrderRequest;
import ua.com.diplomka.orderservice.dto.request.UpdateOrderRequest;
import ua.com.diplomka.orderservice.dto.response.OrderResponse;
import ua.com.diplomka.orderservice.entity.Order;
import ua.com.diplomka.orderservice.entity.OrderFile;

import java.util.List;
import java.util.Optional;

@Component
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);

    @Mapping(target = "fileNames", expression = "java(mapFileNames(order.getFiles()))")
    OrderResponse toOrderResponse(Order order);

    void updateOrder(@MappingTarget Order order, UpdateOrderRequest updateOrderRequest);

    default List<String> mapFileNames(List<OrderFile> files) {
        return Optional.ofNullable(files)
                .orElseGet(List::of)
                .stream()
                .map(OrderFile::getFileName)
                .toList();
    }
}
