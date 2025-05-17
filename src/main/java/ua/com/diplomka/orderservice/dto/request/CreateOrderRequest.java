package ua.com.diplomka.orderservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import ua.com.diplomka.orderservice.enums.WorkType;

import java.time.LocalDateTime;

@Data
public class CreateOrderRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Subject is required")
    private String subject;

    @Min(value = 1,message = "Expected pages must be at least 1")
    @Max(value = 150, message = "Expected pages must be less that 150")
    private int expectedPages;

    @NotBlank(message = "Work type is required")
    private WorkType workType;

    @Future(message = "Deadline must be in the future")
    private LocalDateTime deadline;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be valid")
    private String customerEmail;

    @Pattern(
            regexp = "^\\+380\\d{9}$",
            message = "Phone number must be in Ukrainian format: +380XXXXXXXXX"
    )
    private String customerPhoneNumber;

    @Size(max = 1000, message = "Comment must be no longer than 1000 characters")
    private String comment;
}
