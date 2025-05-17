package ua.com.diplomka.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.diplomka.orderservice.dto.request.CreateOrderRequest;
import ua.com.diplomka.orderservice.dto.request.UpdateOrderRequest;
import ua.com.diplomka.orderservice.dto.response.OrderResponse;
import ua.com.diplomka.orderservice.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestPart @Valid final CreateOrderRequest createOrderRequest,
                                                     @RequestPart(required = false) final List<MultipartFile> files) {
        OrderResponse orderResponse = orderService.createOrder(createOrderRequest, Optional.ofNullable(files));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable final Long id, @RequestBody @Valid final UpdateOrderRequest updateOrderRequest, @RequestPart(required = false) final List<MultipartFile> files) {
        OrderResponse orderResponse = orderService.updateOrder(id, updateOrderRequest, Optional.ofNullable(files));
        return ResponseEntity.ok().body(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable final Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable final Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
