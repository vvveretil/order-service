package ua.com.diplomka.orderservice.dto.response;

import lombok.Data;
import ua.com.diplomka.orderservice.enums.OrderStatus;
import ua.com.diplomka.orderservice.enums.WorkType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;

    private String title;
    private String subject;

    private WorkType workType;
    private OrderStatus status;

    private LocalDateTime deadline;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String customerEmail;
    private String customerPhoneNumber;

    private String comment;

    private List<String> fileNames;
}
