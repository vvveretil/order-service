package ua.com.diplomka.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.diplomka.orderservice.dto.request.CreateOrderRequest;
import ua.com.diplomka.orderservice.dto.request.UpdateOrderRequest;
import ua.com.diplomka.orderservice.dto.response.OrderResponse;
import ua.com.diplomka.orderservice.entity.Order;
import ua.com.diplomka.orderservice.entity.OrderFile;
import ua.com.diplomka.orderservice.enums.OrderStatus;
import ua.com.diplomka.orderservice.exception.OrderNotFoundException;
import ua.com.diplomka.orderservice.mapper.OrderMapper;
import ua.com.diplomka.orderservice.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final FileStorageService fileStorageService;

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest, Optional<List<MultipartFile>> optionalFiles) {
        Order order = orderMapper.toOrder(createOrderRequest);
        order.setStatus(OrderStatus.NEW);

        Order savedOrder = orderRepository.save(order);
        saveOrderFilesIfPresent(savedOrder, optionalFiles);

        return orderMapper.toOrderResponse(savedOrder);
    }

    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest updateOrderRequest, Optional<List<MultipartFile>> optionalFiles) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        orderMapper.updateOrder(order, updateOrderRequest);

        Order savedOrder = orderRepository.save(order);
        saveOrderFilesIfPresent(savedOrder, optionalFiles);

        return orderMapper.toOrderResponse(savedOrder);
    }

    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    public OrderResponse findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return orderMapper.toOrderResponse(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private void saveOrderFilesIfPresent(Order order, Optional<List<MultipartFile>> optionalFiles) {
        optionalFiles
                .filter(files -> !files.isEmpty())
                .ifPresent(nonEmptyFiles -> {
                    List<OrderFile> savedFiles = saveOrderFiles(order.getId(), nonEmptyFiles);
                    attachFilesToOrder(order, savedFiles);
                });
    }

    private List<OrderFile> saveOrderFiles(Long orderId, List<MultipartFile> files) {
        return fileStorageService.saveOrderFiles(orderId, files).stream()
                .map(path -> OrderFile.builder()
                        .fileName(path.getFileName().toString())
                        .path(path.toAbsolutePath().toString())
                        .build()).toList();
    }

    private void attachFilesToOrder(Order order, List<OrderFile> orderFiles) {
        order.getFiles().addAll(orderFiles);
        orderRepository.save(order);
    }
}
