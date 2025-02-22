package com.food.ordering.system.service.domain;

import com.food.ordering.system.service.domain.dto.message.RestaurantApproavalResponse;
import com.food.ordering.system.service.domain.ports.input.message.listener.restaurant.RestaurantApprovalResponseMessageListener;

public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {


    @Override
    public void orderApproved(RestaurantApproavalResponse restaurantApproavalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApproavalResponse restaurantApproavalResponse) {

    }
}
