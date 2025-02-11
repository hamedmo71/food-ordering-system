package com.food.ordering.system.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.create.OrderAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {

        return Restaurant.builder().restaurantId(new RestaurantId(createOrderCommand.getRestaurantId())).products(createOrderCommand.getItems().stream().map(orderItem -> new Product(orderItem.getProductId())).toList()).build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddresstoStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderEntries(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderEntries(List<com.food.ordering.system.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.getProductId().getValue())))
                        .price(new Money(orderItem.getPrice()))
                        .quantity(orderItem.getQuantity())
                        .subTotal(new Money(orderItem.getSubtotal()))
                        .build()).toList();
    }

    private StreetAddress orderAddresstoStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }
}
