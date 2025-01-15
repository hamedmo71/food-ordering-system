package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAdnInitiateOrder(Order order, Restaurant restaurant) {
        restaurant.validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with ID {} is initialized.", order.getId().getValue());
        return new OrderCreatedEvent(ZonedDateTime.now(ZoneId.of("UTC")), order);
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        // TODO: I must write it whit HashMap
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(product -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(product)) {
                currentProduct.updateWithConfirmedNameAndPrice(product.getName(), product.getPrice());
            }
        }));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id {} has been paid", order.getId().getValue());
        return new OrderPaidEvent(ZonedDateTime.now(ZoneId.of("UTC")), order);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id {} has been approved.", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel(failureMessage);
        log.info("Order payment has been cancelled for order with id {}.", order.getId().getValue());
        return new OrderCancelledEvent(ZonedDateTime.now(ZoneId.of("UTC")), order);
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id {} has been cancelled.", order.getId().getValue());
    }
}
