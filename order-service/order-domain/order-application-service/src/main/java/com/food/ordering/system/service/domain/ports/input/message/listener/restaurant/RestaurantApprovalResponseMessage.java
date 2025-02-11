package com.food.ordering.system.service.domain.ports.input.message.listener.restaurant;

import com.food.ordering.system.service.domain.dto.message.RestaurantApproavalResponse;

public interface RestaurantApprovalResponseMessage {

    void orderApproved(RestaurantApproavalResponse restaurantApproavalResponse);
    void orderRejected(RestaurantApproavalResponse restaurantApproavalResponse);
}
