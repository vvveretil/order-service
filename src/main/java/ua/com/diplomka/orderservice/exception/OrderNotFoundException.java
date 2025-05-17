package ua.com.diplomka.orderservice.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long orderId) {
        super("Order with id " + orderId + " not found!");
    }

}
