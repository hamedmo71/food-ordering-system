package com.food.ordering.system.service.domain.dto.message;

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus;
import com.food.ordering.system.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RestaurantApproavalResponse {

    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private BigDecimal price;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessage;
    
}
