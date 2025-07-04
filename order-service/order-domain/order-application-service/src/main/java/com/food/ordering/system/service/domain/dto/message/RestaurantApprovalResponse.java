package com.food.ordering.system.service.domain.dto.message;

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RestaurantApprovalResponse {

    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private BigDecimal price;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
    
}
